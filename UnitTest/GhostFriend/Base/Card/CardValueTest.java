package GhostFriend.Base.Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CardValueTest {

    @Test
    void convertString() {
        assertEquals(CardValue.ACE, CardValue.convertString("ace"));
        assertEquals(CardValue.ACE, CardValue.convertString("Ace"));
        assertEquals(CardValue.ACE, CardValue.convertString("ACE"));
        assertEquals(CardValue.TWO, CardValue.convertString("two"));
        assertEquals(CardValue.THREE, CardValue.convertString("three"));
        assertEquals(CardValue.FOUR, CardValue.convertString("four"));
        assertEquals(CardValue.FIVE, CardValue.convertString("five"));
        assertEquals(CardValue.SIX, CardValue.convertString("six"));
        assertEquals(CardValue.SEVEN, CardValue.convertString("seven"));
        assertEquals(CardValue.EIGHT, CardValue.convertString("eight"));
        assertEquals(CardValue.NINE, CardValue.convertString("nine"));
        assertEquals(CardValue.TEN, CardValue.convertString("ten"));
        assertEquals(CardValue.JACK, CardValue.convertString("jack"));
        assertEquals(CardValue.QUEEN, CardValue.convertString("queen"));
        assertEquals(CardValue.KING, CardValue.convertString("king"));
        assertNull(CardValue.convertString("joker"));
    }
}