package GhostFriend.Base.Deck;

import GhostFriend.Base.Card.Card;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DeckTest {

    private Deck deck = new Deck();

    @Test
    void drawCard() {
        Card card = deck.DrawCard();
        assertTrue(Card.IsValidCard(card));
    }
}