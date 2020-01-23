package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.CardSuit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuleTest {
    private Rule rule = new Rule();

    @Test
    void getNumOfCardsPerPerson() {
        assertEquals(10, Rule.getNumOfCardsPerPerson());
    }

    @Test
    void isValidContract() {
        Contract currentContract = new Contract();
        Contract newContract = new Contract();

        currentContract.declare(CardSuit.CLUB, 14);

        newContract.declare(CardSuit.JOKER, 15);
        assertEquals(ContractValidation.GIRU, rule.isValidContract(currentContract, newContract));

        newContract.declare(CardSuit.HEART, 5);
        assertEquals(ContractValidation.MINIMUM, rule.isValidContract(currentContract, newContract));

        newContract.declare(CardSuit.SPADE, 14);
        assertEquals(ContractValidation.SCORE, rule.isValidContract(currentContract, newContract));

        newContract.declare(CardSuit.SPADE, 17);
        assertEquals(ContractValidation.VALID, rule.isValidContract(currentContract, newContract));
    }
}