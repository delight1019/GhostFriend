package GhostFriend.Base.Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    private Card invalidCard = new Card(CardSuit.JOKER, CardValue.SEVEN);
    private Card clubAce = new Card(CardSuit.CLUB, CardValue.ACE);
    private Card joker = new Card(CardSuit.JOKER, CardValue.JOKER);

    @Test
    void isValidCard() {
        assertTrue(Card.IsValidCard(clubAce));
        assertTrue(Card.IsValidCard(joker));
        assertFalse(Card.IsValidCard(invalidCard));

        assertFalse(Card.IsValidCard(CardSuit.JOKER, CardValue.ACE));
        assertFalse(Card.IsValidCard(CardSuit.SPADE, CardValue.JOKER));
        assertTrue(Card.IsValidCard(CardSuit.SPADE, CardValue.ACE));
    }

    @Test
    void testToString() {
        assertEquals("CLUB ACE", clubAce.toString());
        assertEquals("JOKER", joker.toString());
    }

    @Test
    void isScoreCard() {
        assertTrue(clubAce.IsScoreCard());
        assertFalse(joker.IsScoreCard());
    }
}