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
    private Player winner;
    private int currentPhaseScore;

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
        setWinner(player);

        currentPhaseScore += rule.getScore(player.getSubmittedCard());

        setNextPlayer(player);
    }

    private void setWinner(Player player) {
        if (rule.isWinnerCard(winner.getSubmittedCard(), player.getSubmittedCard())) {
            winner = player;
        }
    }

    public boolean isPhaseFinished() {
        boolean isFinished = true;

        for (Player player : players) {
            isFinished = isFinished & (player.isSubmitted());
        }

        return isFinished;
    }

    public Player getWinner() {
        return winner;
    }

    public int getPhaseScore() {
        return currentPhaseScore;
    }

    public boolean isAllPhaseFinished() {
        return (winner.getCardList().isEmpty());
    }

    public void clearPhase() {
        currentPlayer = winner;
        currentPhaseScore = 0;
    }

    public GameController(List<Player> players, Rule rule, Player declarer, Player friend) {
        this.players = players;
        this.rule = rule;
        this.declarer = declarer;
        this.friend = friend;
        this.currentPlayer = declarer;
        this.winner = declarer;
        this.currentPhaseScore = 0;
    }
}
