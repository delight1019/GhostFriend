package GhostFriend.Base.Game;

import GhostFriend.Base.IOController.IOController;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private Rule Rule;
    private List<Player> Players;

    public void StartPlaying(int numOfPlayers) {
        IOController.startGame();

        Rule = new Rule();
        Players = new ArrayList<>();

        for (int i = 0; i < numOfPlayers; i++) {
            Players.add(new Player("Player" + (i + 1)));
            IOController.JoinPlayer(Players.get(i).getName());
        }
    }
}
