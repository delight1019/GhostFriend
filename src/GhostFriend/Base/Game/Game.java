package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Deck.Deck;
import GhostFriend.Base.IOController.IOController;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.ContractValidation;
import GhostFriend.Base.Rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static final int PLAYER_NUMBER = 5;

    private Rule rule;
    private Deck deck;
    private final List<Player> players;
    private Player declarer;
    private Player friend;
    private int numOfPlayers;

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

    public void StartPlaying_old(int numOfPlayers) {
        IOController.startGame();

        rule = new Rule();
        deck = new Deck();
        //players = new ArrayList<>();
        declarer = new Player("Declarer");
        friend = null;
        this.numOfPlayers = numOfPlayers;

        IOController.handCardsOut();

        for (int i = 0; i < numOfPlayers; i++) {
            //IOController.checkCards(players.get(i));
        }

        for (int i = 0; i < numOfPlayers; i++) {
            //if (rule.isDealMiss(players.get(i).getCardList())) {
//                if (IOController.askDealMiss(players.get(i))) {
                    //StartPlaying(numOfPlayers);
                //}
            //}
        }
    }

    public void startPlaying() {
        distributeCards();
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
                    player.receiveCard(deck.drawCard());
                }
            }
        }
    }

    public void determineDeclarer() {
        int playerIndex = 0;
        int numOfPass = 0;

        IOController.printCurrentMinContractScore(rule.getMinContractScore());

        while (true) {
            Player currentPlayer = null;

            String userInput = IOController.askBidding(currentPlayer, declarer.getContract());

            if (userInput.toUpperCase().equals("PASS")) {
                currentPlayer.getContract().Initialize();
                numOfPass++;
            } else {
                currentPlayer.declareContract(IOController.parseContract(userInput));

                if (rule.isValidContract(declarer.getContract(), currentPlayer.getContract()) == ContractValidation.VALID) {
                    declarer = currentPlayer;
                    numOfPass = 0;
                } else {
                    IOController.invalidContract(rule.isValidContract(declarer.getContract(), currentPlayer.getContract()), rule.getMinContractScore());
                    continue;
                }
            }

            playerIndex++;

            if (playerIndex == numOfPlayers) {
                playerIndex = 0;
            }

            if ((numOfPass == numOfPlayers - 1) && declarer.getContract().getDeclared()){
                break;
            }

            if (numOfPass == numOfPlayers) {
                rule.decreaseMinContractScore();
                IOController.printCurrentMinContractScore(rule.getMinContractScore());
                numOfPass = 0;
            }
        }

        IOController.determineDeclarer(declarer);
    }

    public void confirmDeclarerCards() {
        IOController.confirmDeclarerCards(declarer);

        for (int i = 0; i < 3; i++) {
            declarer.receiveCard(deck.drawCard());
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

        rule.setMighty(declarer.getContract().getGiru());
        rule.setJokerCall(declarer.getContract().getGiru());
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
