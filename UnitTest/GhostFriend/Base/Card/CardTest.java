package GhostFriend.Base.Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CardTest {

    @Test
    void isValidCard() {
        assertFalse(Card.IsValidCard(CardSuit.JOKER, CardValue.ACE));
        assertFalse(Card.IsValidCard(CardSuit.SPADE, CardValue.JOKER));
        assertTrue(Card.IsValidCard(CardSuit.SPADE, CardValue.ACE));
    }
}