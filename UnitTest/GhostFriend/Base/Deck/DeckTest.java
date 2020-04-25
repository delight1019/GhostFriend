package GhostFriend.Base.Deck;

import GhostFriend.Base.Card.Card;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeckTest {

    private Deck deck = new Deck();

    @Test
    @DisplayName("Draw and Return Card to Deck")
    void drawCard() {
        Card card = deck.draw();
        assertTrue(Card.IsValidCard(card));
        assertFalse(deck.contains(card));

        deck.returnCard(card);
        assertTrue(deck.contains(card));
    }
}