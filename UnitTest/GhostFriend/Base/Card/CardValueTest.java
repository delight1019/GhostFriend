package GhostFriend.Base.Card;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CardValueTest {

    @Test
    void convertString() {
        assertEquals(CardValue.ACE, CardValue.convertString("ace"));
        assertEquals(CardValue.ACE, CardValue.convertString("Ace"));
        assertEquals(CardValue.ACE, CardValue.convertString("ACE"));
    }
}