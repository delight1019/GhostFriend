package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    void decreaseMinContractScore() {
        rule.decreaseMinContractScore();
        assertEquals(12, rule.getMinContractScore());
    }

    @Test
    void isDealMiss() {
        List<Card> cardList = new ArrayList<>();
        cardList.add(new Card(CardSuit.SPADE, CardValue.ACE));
        cardList.add(new Card(CardSuit.SPADE, CardValue.TEN));
        cardList.add(new Card(CardSuit.SPADE, CardValue.SEVEN));
        cardList.add(new Card(CardSuit.SPADE, CardValue.EIGHT));
        cardList.add(new Card(CardSuit.SPADE, CardValue.TWO));
        cardList.add(new Card(CardSuit.SPADE, CardValue.THREE));
        cardList.add(new Card(CardSuit.SPADE, CardValue.JACK));
        cardList.add(new Card(CardSuit.JOKER, CardValue.JOKER));
        cardList.add(new Card(CardSuit.SPADE, CardValue.FIVE));
        cardList.add(new Card(CardSuit.SPADE, CardValue.SIX));
        assertTrue(rule.isDealMiss(cardList));
    }
}