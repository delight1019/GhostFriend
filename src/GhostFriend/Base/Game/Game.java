package GhostFriend.Base.Game;

import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;

import java.util.List;

public class Game {
    private Rule Rule;
    private List<Player> Players;

    public void StartPlaying(int numOfPlayers) {
        Rule = new Rule();

        for (int i = 0; i < numOfPlayers; i++) {
            Players.add(new Player("Player" + Integer.toString(i + 1)));
        }
    }
}
