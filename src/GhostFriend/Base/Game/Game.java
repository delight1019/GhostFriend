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

    public void StartPlaying(int numOfPlayers) {
        IOController.startGame();

        rule = new Rule();
        deck = new Deck();
        players = new ArrayList<>();

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
}
