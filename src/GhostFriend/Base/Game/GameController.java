package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Event.GameEventBus;
import GhostFriend.Base.Event.GameEvents;
import GhostFriend.Base.Event.EventType;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;

import java.util.ArrayList;
import java.util.List;

public class GameController {
    private final List<Player> players;
    private final Rule rule;
    private final Player declarer;
    private final Player friend;
    private final List<Card> submittedCards;
    private Player currentPlayer;
    private int currentTrick;
    private GameEventBus eventBus;

    public GameController(List<Player> players, Rule rule, Player declarer, Player friend) {
        this.players = players;
        this.rule = rule;
        this.declarer = declarer;
        this.friend = friend;
        this.submittedCards = new ArrayList<>();
        this.currentPlayer = declarer;
        this.currentTrick = 0;
        this.eventBus = new GameEventBus();
    }

    public GameEventBus getEventBus() {
        return eventBus;
    }

    public void setEventBus(GameEventBus eventBus) {
        this.eventBus = eventBus;
    }

    public boolean submitCard(Player player, Card card) {
        if (player != currentPlayer) {
            return false;
        }

        if (!isValidCard(player, card)) {
            return false;
        }

        submittedCards.add(card);
        player.removeCard(card);
        eventBus.publish(GameEvents.cardSubmitted(player, card));

        if (submittedCards.size() == players.size()) {
            Player winner = determineWinner();
            winner.increaseScore(calculateTrickScore());
            eventBus.publish(GameEvents.trickCompleted(winner, submittedCards));
            
            submittedCards.clear();
            currentTrick++;
            
            if (currentTrick >= 13) {
                eventBus.publish(GameEvents.gamePhaseChanged(EventType.END_GAME.getValue()));
                return true;
            }
            
            currentPlayer = winner;
        } else {
            currentPlayer = getNextPlayer();
        }

        return true;
    }

    private boolean isValidCard(Player player, Card card) {
        if (submittedCards.isEmpty()) {
            return true;
        }

        CardSuit leadSuit = submittedCards.get(0).getSuit();
        boolean hasLeadSuit = false;

        for (Card playerCard : player.getCards()) {
            if (playerCard.getSuit() == leadSuit) {
                hasLeadSuit = true;
                break;
            }
        }

        return !hasLeadSuit || card.getSuit() == leadSuit;
    }

    private Player determineWinner() {
        Card winningCard = submittedCards.get(0);
        Player winner = players.get(0);

        for (int i = 1; i < submittedCards.size(); i++) {
            Card currentCard = submittedCards.get(i);
            if (rule.isHigherCard(currentCard, winningCard)) {
                winningCard = currentCard;
                winner = players.get(i);
            }
        }

        return winner;
    }

    private int calculateTrickScore() {
        int score = 0;
        for (Card card : submittedCards) {
            score += rule.getCardScore(card);
        }
        return score;
    }

    private Player getNextPlayer() {
        int currentIndex = players.indexOf(currentPlayer);
        return players.get((currentIndex + 1) % players.size());
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean isGameEnd() {
        return currentTrick >= 13;
    }
}
