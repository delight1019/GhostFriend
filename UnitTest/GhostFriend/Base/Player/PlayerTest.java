package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Rule.Contract;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player = new Player("testPlayer");

    @Test
    void getName() {
        assertEquals("testPlayer", player.getName());
    }

    @Test
    @DisplayName("Receive and discard card")
    void receiveCard() {
        Card card1 = new Card(CardSuit.JOKER, CardValue.JOKER);
        Card card2 = new Card(CardSuit.CLUB, CardValue.SEVEN);

        player.receiveCard(card1);
        player.receiveCard(card2);

        assertTrue(player.hasCard(card1));
        assertTrue(player.hasCard(card2));

        player.discardCard(card2);
        player.discardCard(card1);

        assertFalse(player.hasCard(card1));
        assertFalse(player.hasCard(card2));
        assertEquals(2, player.getDiscardedCardNum());
    }

    @Test
    void getCardListInfo() {
        Card card1 = new Card(CardSuit.SPADE, CardValue.ACE);
        Card card2 = new Card(CardSuit.CLUB, CardValue.SEVEN);

        player.receiveCard(card1);
        player.receiveCard(card2);

        assertEquals("SPADE ACE/CLUB SEVEN", player.getCardListInfo("/"));

        player.discardCard(card1);
        player.discardCard(card2);
    }

    @Test
    void clearGameInfo() {
        player.receiveCard(new Card(CardSuit.SPADE, CardValue.ACE));
        player.checkDealMiss(true);

        assertFalse(player.getCardList().isEmpty());
        assertNotEquals(DealMissStatus.CHECKING, player.getDealMissStatus());

        player.clearGameInfo();

        assertTrue(player.getCardList().isEmpty());
        assertEquals(DealMissStatus.CHECKING, player.getDealMissStatus());
    }

    @Test
    void checkDealMiss() {
        player.clearGameInfo();
        assertEquals(DealMissStatus.CHECKING, player.getDealMissStatus());

        player.checkDealMiss(true);
        assertEquals(DealMissStatus.MISS, player.getDealMissStatus());

        player.checkDealMiss(false);
        assertEquals(DealMissStatus.OK, player.getDealMissStatus());
    }

    @Test
    @DisplayName("Contract Declaration")
    void declareContract() {
        player.declareContract(CardSuit.SPADE, 15);
        assertEquals("SPADE 15", player.getContract().toString(" "));

        player.declareContract(null, 18);
        assertEquals("No giru 18", player.getContract().toString(" "));

        Contract contract = new Contract();
        contract.declare(CardSuit.SPADE, 15);
        player.declareContract(contract);

        assertTrue(player.getContract().isEquals(contract));

        contract.declare(null, 15);
        player.declareContract(contract);
        assertTrue(player.getContract().isEquals(contract));

        player.passContractDeclaration();
        assertFalse(player.getContract().getDeclared());
    }

    @Test
    @DisplayName("Card Submission")
    void submitCard() {
        Card card1 = new Card(CardSuit.SPADE, CardValue.THREE);
        Card card2 = new Card(CardSuit.HEART, CardValue.ACE);
        player.receiveCard(card1);
        player.receiveCard(card2);

        assertFalse(player.isSubmitted());

        player.submitCard(card2);

        assertTrue(player.isSubmitted());
        assertFalse(player.hasCard(card2));
        assertEquals(card2, player.getSubmittedCard());

        player.discardCard(card1);
        player.discardCard(card2);
    }
}