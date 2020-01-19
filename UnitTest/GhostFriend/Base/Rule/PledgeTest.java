package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class PledgeTest {

    private Pledge pledge = new Pledge();

    @Test
    void makePledge() {
        assertFalse(pledge.makePledge(CardSuit.CLUB, 10));
        assertFalse(pledge.makePledge(CardSuit.JOKER, 15));
    }
}