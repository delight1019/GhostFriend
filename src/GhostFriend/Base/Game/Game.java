package GhostFriend.Base.Game;

import GhostFriend.Base.Deck.Deck;
import GhostFriend.Base.IOController.IOController;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Contract;
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
    private Contract gameContract;

    public void StartPlaying(int numOfPlayers) {
        IOController.startGame();

        rule = new Rule();
        deck = new Deck();
        players = new ArrayList<>();
        declarer = null;
        friend = null;
        this.numOfPlayers = numOfPlayers;
        this.gameContract = new Contract();

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
        boolean isDetermiend = false;

        while (!isDetermiend) {
            for (int i = 0; i < numOfPlayers; i++) {
                IOController.askBidding(players.get(i), gameContract);
                String userInput = IOController.scanner.nextLine();

                if (userInput == "PASS") {
                    continue;
                } else {
                    // To do: 공약 선언에 대한 처리와 잘못된 입력이 들어왔을 경우에 대한 처리
                }
            }
        }

    }
}
