package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Deck.Deck;
import GhostFriend.Base.IOController.IOController;
import GhostFriend.Base.Player.DealMissStatus;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;
import GhostFriend.Server.GameParams;
import GhostFriend.Server.MainServer;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int PLAYER_NUMBER = 2;
    private static final int DECLARER_ADDITIONAL_CARD_NUM = 3;

    private Rule rule;
    private Deck deck;
    private final List<Player> players;
    private Player declarer;
    private Player friend;
    private int numOfPlayers;
    private ContractDeclarator contractDeclarator;

    public Game() {
        rule = new Rule();
        deck = new Deck();
        players = new ArrayList<>();
        declarer = new Player("Declarer");
        friend = null;
        this.numOfPlayers = 0;
    }

    public boolean isAllPlayersEntered() {
        synchronized (this) {
            return (players.size() == PLAYER_NUMBER);
        }
    }

    public String getPlayersInfo(String delimiter) {
        StringBuilder playersInfo = new StringBuilder();

        synchronized (players) {
            for (Player player : players) {
                playersInfo.append(player.getName());
                playersInfo.append(delimiter);
            }
        }

        return playersInfo.toString();
    }

    public void clear() {
        deck = new Deck();

        synchronized (players) {
            for (Player player : players) {
                player.clearGameInfo();
            }
        }
    }

    public Boolean isDealMiss(Player player) {
        return rule.isDealMiss(player.getCardList());
    }

    public DealMissStatus isDealMissDeclared() {
        synchronized (players) {
            for (Player player : players) {
                if (player.getDealMissStatus() == DealMissStatus.CHECKING) {
                    return DealMissStatus.CHECKING;
                }
                else if (player.getDealMissStatus() == DealMissStatus.MISS) {
                    return DealMissStatus.MISS;
                }
            }
        }

        return DealMissStatus.OK;
    }

    public Player addPlayer(String name) {
        synchronized (players) {
            if (players.size() >= PLAYER_NUMBER) {
                return null;
            } else {
                Player player = new Player(name);
                players.add(player);

                return player;
            }
        }
    }

    public void removePlayer(Player player) {
        synchronized (players) {
            players.remove(player);
        }
    }

    public void distributeCards() {
        synchronized (players) {
            for (Player player : players) {
                for (int i = 0; i < Rule.getNumOfCardsPerPerson(); i++) {
                    player.receiveCard(deck.draw());
                }
            }
        }
    }

    public void startContractDeclaration() {
        contractDeclarator = new ContractDeclarator(rule, players);
        askContractDeclaring();
    }

    public void passContractDeclaration(Player player) {
        contractDeclarator.passDeclaration(player);

        if (contractDeclarator.isFinished()) {
            confirmDeclarer(contractDeclarator.getCurrentDeclarer());
            confirmDeclarerCards();
        } else {
            askContractDeclaring();
        }
    }

    private void askContractDeclaring() {
        synchronized (players) {
            for (Player player : players) {
                if (player == contractDeclarator.getDeclaringPlayer()) {
                    MainServer.getInstance().broadcast(player, GameParams.ASK_CONTRACT,
                                                       contractDeclarator.getCurrentContract(GameParams.DATA_DELIMITER) + GameParams.DATA_DELIMITER + contractDeclarator.getMinContractScore().toString());
                }
                else {
                    MainServer.getInstance().broadcast(player, GameParams.OTHER_PLAYER_ASKING_CONTRACT,
                                                    contractDeclarator.getCurrentContract(GameParams.DATA_DELIMITER) + GameParams.DATA_DELIMITER +
                                                         contractDeclarator.getMinContractScore().toString() + GameParams.DATA_DELIMITER +
                                                         contractDeclarator.getDeclaringPlayer().getName());
                }
            }
        }
    }

    public void declareContract(Player player, String contractData) {
        String[] contractInfo = contractData.split(GameParams.DATA_DELIMITER);
        contractDeclarator.declare(player, CardSuit.convertString(contractInfo[0]), Integer.parseInt(contractInfo[1]));

        if (contractDeclarator.isFinished()) {
            confirmDeclarer(contractDeclarator.getCurrentDeclarer());
            confirmDeclarerCards();
        } else {
            askContractDeclaring();
        }
    }

    private void confirmDeclarer(Player player) {
        this.declarer = player;
        MainServer.getInstance().broadcast(GameParams.CASTER_DECLARED, declarer.getContract().toString(GameParams.DATA_DELIMITER));
    }

    private void confirmDeclarerCards() {
        for (int i = 0; i < DECLARER_ADDITIONAL_CARD_NUM; i++) {
            declarer.receiveCard(deck.draw());
        }

        synchronized (players) {
            for (Player player : players) {
                if (player == declarer) {
                    MainServer.getInstance().broadcast(declarer, GameParams.SELECT_CARDS_TO_DISCARD, declarer.getCardListInfo(GameParams.DATA_DELIMITER));
                }
                else {
                    MainServer.getInstance().broadcast(player, GameParams.START_DECLARER_CARD_SELECTION, "");
                }
            }
        }
    }

    public void confirmDeclarerCardsOld() {
        IOController.confirmDeclarerCards(declarer);

        for (int i = 0; i < 3; i++) {
            declarer.receiveCard(deck.draw());
        }

        for (int i = 0; i < 3; i++) {
            IOController.checkCards(declarer);
            Card discardingCard = IOController.askDiscardingCard(declarer);

            while (!declarer.hasCard(discardingCard)) {
                IOController.doNotHaveCard(discardingCard);
                IOController.checkCards(declarer);
                discardingCard = IOController.askDiscardingCard(declarer);
            }

            declarer.discardCard(discardingCard);
            deck.returnCard(discardingCard);
        }

        if (IOController.askGiruChange(declarer, declarer.getContract())) {
            declarer.declareContract(IOController.askGiruToChange(declarer), declarer.getContract().getScore() + 2);
        }

        rule.setGiru(declarer.getContract().getGiru());
    }

    public void determineFriend() {
        Card friendCard = IOController.askFriendCard(declarer, rule.getMighty(), rule.getJokerCall());

        for (int i = 0; i < numOfPlayers; i++) {
            Player currentPlayer = null;

            if (currentPlayer != declarer) {
                if (currentPlayer.hasCard(friendCard)) {
                    friend = currentPlayer;
                }
            }
        }
    }
}
