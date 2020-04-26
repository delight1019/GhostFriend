package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Deck.Deck;
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
    private GameController gameController;

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
        MainServer.getInstance().broadcast(GameParams.CASTER_DECLARED, declarer.getName() + GameParams.DATA_DELIMITER + declarer.getContract().toString(GameParams.DATA_DELIMITER));
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

    public void discardCard(Player player, String cardData) {
        String[] cardInfo = cardData.split(GameParams.DATA_DELIMITER);

        Card card = new Card(CardSuit.convertString(cardInfo[0]), CardValue.convertString(cardInfo[1]));

        player.discardCard(card);
        deck.returnCard(card);

        if (player.getDiscardedCardNum() < DECLARER_ADDITIONAL_CARD_NUM) {
            MainServer.getInstance().broadcast(declarer, GameParams.SELECT_CARDS_TO_DISCARD, declarer.getCardListInfo(GameParams.DATA_DELIMITER));
        } else {
            MainServer.getInstance().broadcast(declarer, GameParams.UPDATE_CARD_LIST, declarer.getCardListInfo(GameParams.DATA_DELIMITER));
            MainServer.getInstance().broadcast(declarer, GameParams.ASK_GIRU_CHANGE, "");
        }
    }

    public void confirmGiru() {
        rule.setGiru(declarer.getContract().getGiru());
        MainServer.getInstance().broadcast(GameParams.CONFIRM_CONTRACT, declarer.getContract().toString(GameParams.DATA_DELIMITER));

        askFriendCard();
    }

    public void confirmGiru(String giruData) {
        CardSuit giru = CardSuit.convertString(giruData);
        declarer.declareContract(giru, declarer.getContract().getScore() + 2);

        confirmGiru();
    }

    private void askFriendCard() {
        MainServer.getInstance().broadcast(declarer, GameParams.ASK_FRIEND_CARD, rule.getMighty().toString() + GameParams.DATA_DELIMITER + rule.getJokerCall().toString());
    }

    public void determineFriend(String cardData) {
        String[] cardInfo = cardData.split(GameParams.DATA_DELIMITER);

        Card friendCard = new Card(CardSuit.convertString(cardInfo[0]), CardValue.convertString(cardInfo[1]));

        synchronized (players) {
            for (Player player: players) {
                if (player.hasCard(friendCard)) {
                    friend = player;
                }
            }
        }

        synchronized (players) {
            for (Player player: players) {
                if (player == friend) {
                    MainServer.getInstance().broadcast(player, GameParams.NOTIFY_FRIEND, "");
                }

                MainServer.getInstance().broadcast(player, GameParams.CONFIRM_FRIEND, friendCard.toString());
                MainServer.getInstance().broadcast(player, GameParams.START_PLAYING, "");
            }
        }

        startPlaying();
    }

    private void startPlaying() {
        gameController = new GameController(players, rule, declarer, friend);
        MainServer.getInstance().broadcast(gameController.getCurrentPlayer(), GameParams.ASK_CARD, "");
    }

    public void submitCard(Player player, String cardData) {
        String[] cardInfo = cardData.split(GameParams.DATA_DELIMITER);
        Card submittedCard = new Card(CardSuit.convertString(cardInfo[0]), CardValue.convertString(cardInfo[1]));

        gameController.submitCard(player, submittedCard);

        MainServer.getInstance().broadcast(player, GameParams.UPDATE_CARD_LIST, player.getCardListInfo(GameParams.DATA_DELIMITER));

        BroadcastCardSubmission(player.getName(), submittedCard);

        if (gameController.isPhaseFinished()) {
            Player winner = gameController.getWinner();
            Integer phaseScore = gameController.getPhaseScore();

            winner.increaseScore(phaseScore);

            MainServer.getInstance().broadcast(GameParams.NOTIFY_PHASE_WINNER, winner.getName() + GameParams.DATA_DELIMITER + phaseScore.toString());

            if (gameController.isAllPhaseFinished()) {
                NotifyGameWinner();
            }
            else {
                gameController.clearPhase();
                MainServer.getInstance().broadcast(gameController.getCurrentPlayer(), GameParams.ASK_CARD, "");
            }
        }
        else {
            MainServer.getInstance().broadcast(gameController.getCurrentPlayer(), GameParams.ASK_CARD, "");
        }
    }

    private void BroadcastCardSubmission(String playerName, Card card) {
        synchronized (players) {
            for (Player player: players) {
                MainServer.getInstance().broadcast(player, GameParams.NOTIFY_CARD_SUBMISSION, playerName + GameParams.DATA_DELIMITER + card.toString());
            }
        }
    }

    private void NotifyGameWinner() {
        if (rule.isWinner(declarer)) {
            MainServer.getInstance().broadcast(GameParams.NOTIFY_GAME_WINNER, GameParams.DECLARER_WIN + GameParams.DATA_DELIMITER + friend.getName());
        }
        else {
            MainServer.getInstance().broadcast(GameParams.NOTIFY_GAME_WINNER, GameParams.DECLARER_LOSE + GameParams.DATA_DELIMITER + friend.getName());
        }
    }
}
