package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;

import java.util.List;

public class GameController {
    private List<Player> players;
    private Rule rule;
    private Player declarer;
    private Player friend;
    private Player currentPlayer;

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setNextPlayer(Player submittedPlayer) {
        int submittedIndex = players.indexOf(submittedPlayer);
        int nextPlayerIndex = submittedIndex + 1;

        if (nextPlayerIndex == players.size()) {
            nextPlayerIndex = 0;
        }

        currentPlayer = players.get(nextPlayerIndex);
    }

    public void submitCard(Player player, Card card) {
        player.submitCard(card);
        setNextPlayer(player);
    }

    public GameController(List<Player> players, Rule rule, Player declarer, Player friend) {
        this.players = players;
        this.rule = rule;
        this.declarer = declarer;
        this.friend = friend;
        this.currentPlayer = declarer;
    }
}
