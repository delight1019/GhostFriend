package GhostFriend.Base.Rule;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RuleTest {
    private Rule rule = new Rule();

    @Test
    @DisplayName("Mighty and Jokercall change by giru")
    void MightyTest() {
        assertNull(rule.getMighty());
        assertNull(rule.getJokerCall());

        rule.setGiru(CardSuit.SPADE);
        assertEquals(CardSuit.DIAMOND, rule.getMighty().getCardSuit());
        assertEquals(CardValue.ACE, rule.getMighty().getCardValue());
        assertEquals(CardSuit.CLUB, rule.getJokerCall().getCardSuit());
        assertEquals(CardValue.THREE, rule.getJokerCall().getCardValue());

        rule.setGiru(CardSuit.CLUB);
        assertEquals(CardSuit.SPADE, rule.getMighty().getCardSuit());
        assertEquals(CardValue.ACE, rule.getMighty().getCardValue());
        assertEquals(CardSuit.HEART, rule.getJokerCall().getCardSuit());
        assertEquals(CardValue.THREE, rule.getJokerCall().getCardValue());
    }

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

        newContract.declare(null, 18);
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

    @Test
    @DisplayName("Winner Determination")
    void isWinner() {
        rule.setGiru(CardSuit.CLUB);

        assertTrue(rule.isWinnerCard(new Card(CardSuit.JOKER, CardValue.JOKER), new Card(CardSuit.SPADE, CardValue.ACE)));
        assertFalse(rule.isWinnerCard(new Card(CardSuit.SPADE, CardValue.ACE), new Card(CardSuit.CLUB, CardValue.ACE)));
        assertFalse(rule.isWinnerCard(new Card(CardSuit.JOKER, CardValue.JOKER), new Card(CardSuit.CLUB, CardValue.ACE)));
        assertTrue(rule.isWinnerCard(new Card(CardSuit.CLUB, CardValue.ACE), new Card(CardSuit.JOKER, CardValue.JOKER)));
        assertTrue(rule.isWinnerCard(new Card(CardSuit.CLUB, CardValue.THREE), new Card(CardSuit.CLUB, CardValue.ACE)));
        assertFalse(rule.isWinnerCard(new Card(CardSuit.CLUB, CardValue.JACK), new Card(CardSuit.CLUB, CardValue.FIVE)));
        assertTrue(rule.isWinnerCard(new Card(CardSuit.DIAMOND, CardValue.JACK), new Card(CardSuit.CLUB, CardValue.FIVE)));
        assertFalse(rule.isWinnerCard(new Card(CardSuit.CLUB, CardValue.JACK), new Card(CardSuit.HEART, CardValue.FIVE)));
        assertTrue(rule.isWinnerCard(new Card(CardSuit.HEART, CardValue.THREE), new Card(CardSuit.HEART, CardValue.FIVE)));
        assertFalse(rule.isWinnerCard(new Card(CardSuit.HEART, CardValue.KING), new Card(CardSuit.SPADE, CardValue.TEN)));
    }

    @Test
    void getScore() {
        assertEquals(1, rule.getScore(new Card(CardSuit.CLUB, CardValue.JACK)));
        assertEquals(0, rule.getScore(new Card(CardSuit.CLUB, CardValue.THREE)));
    }
}