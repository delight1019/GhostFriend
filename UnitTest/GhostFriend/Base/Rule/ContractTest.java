package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ContractTest {

    private Contract contract = new Contract();

    @Test
    void bid() {
        assertFalse(contract.bid(CardSuit.CLUB, 10));
        assertFalse(contract.bid(CardSuit.JOKER, 15));
    }
}