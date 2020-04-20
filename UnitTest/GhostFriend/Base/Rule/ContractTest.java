package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContractTest {

    private Contract emptyContract = new Contract();
    private Contract testContract = new Contract();
    private Contract invalidContract = new Contract();

    @Test
    void initialize() {
        testContract.declare(CardSuit.SPADE, 15);
        testContract.initialize();
        assertNull(testContract.getGiru());
        assertEquals(-1, testContract.getScore());
        assertFalse(testContract.getDeclared());
    }

    @Test
    void declare() {
        testContract.declare(CardSuit.SPADE, 15);
        assertEquals(CardSuit.SPADE, testContract.getGiru());
        assertEquals(15, testContract.getScore());
        assertTrue(testContract.getDeclared());
    }

    @Test
    void isValidGiru() {
        testContract.declare(CardSuit.SPADE, 15);
        invalidContract.declare(CardSuit.JOKER, 20);
        assertTrue(Contract.isValidGiru(testContract));
        assertFalse(Contract.isValidGiru(invalidContract));
    }

    @Test
    void testToString() {
        assertEquals("선언되지 않았습니다.", emptyContract.toString());

        testContract.declare(CardSuit.SPADE, 15);
        assertEquals("SPADE 15", testContract.toString());

        testContract.declare(null, 18);
        assertEquals("No giru 18", testContract.toString());
    }
}