package GhostFriend.Base.Game;

import GhostFriend.Base.Deck.Deck;
import GhostFriend.Base.IOController.IOController;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Rule rule;
    private Deck deck;
    private List<Player> players;
    private Player declarer;
    private Player friend;
    private int numOfPlayers;

    public void StartPlaying(int numOfPlayers) {
        IOController.startGame();

        rule = new Rule();
        deck = new Deck();
        players = new ArrayList<>();
        declarer = new Player("Declarer");
        friend = null;
        this.numOfPlayers = numOfPlayers;

        for (int i = 0; i < numOfPlayers; i++) {
            players.add(new Player("Player" + (i + 1)));
            IOController.joinPlayer(players.get(i).getName());
        }

        IOController.handCardsOut();

        for (int i = 0; i < numOfPlayers; i++) {
            for (int j = 0; j < Rule.getNumOfCardsPerPerson(); j++) {
                players.get(i).receiveCard(deck.DrawCard());
            }
        }

        for (int i = 0; i < numOfPlayers; i++) {
            IOController.checkCards(players.get(i));
        }
    }

    public void determineDeclarer() {
        Integer declareCount = 0;
        int playerIndex = 0;
        int numOfPass = 0;

        while (true) {
            Player currentPlayer = players.get(playerIndex);

            IOController.askBidding(currentPlayer, declarer.getContract());
            String userInput = IOController.scanner.nextLine();

            if (userInput.toUpperCase().equals("PASS")) {
                currentPlayer.getContract().Initialize();
                numOfPass++;
            } else {
                // To do: 잘못된 입력이 들어왔을 경우에 대한 처리

                currentPlayer.declareContract(IOController.parseContract(userInput));

                if (rule.IsValidContract(declarer.getContract(), currentPlayer.getContract())) {
                    declarer = currentPlayer;
                    numOfPass = 0;
                }
            }

            playerIndex++;

            if (playerIndex == numOfPlayers) {
                playerIndex = 0;
            }

            if (numOfPass == numOfPlayers - 1) {
                break;
            }
        }

        IOController.determineDeclarer(declarer);
    }
}
