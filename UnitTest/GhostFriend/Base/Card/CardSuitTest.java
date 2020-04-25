package GhostFriend.Base.Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardSuitTest {

    @Test
    void convertString() {
        assertEquals(CardSuit.SPADE, CardSuit.convertString("Spade"));
        assertEquals(CardSuit.SPADE, CardSuit.convertString("spade"));
        assertEquals(CardSuit.JOKER, CardSuit.convertString("JOKER"));
        assertEquals(CardSuit.JOKER, CardSuit.convertString("joker"));
        assertEquals(CardSuit.DIAMOND, CardSuit.convertString("diamond"));
        assertEquals(CardSuit.CLUB, CardSuit.convertString("club"));
        assertEquals(CardSuit.HEART, CardSuit.convertString("heart"));
        assertNull(CardSuit.convertString("Invalid"));
    }
}