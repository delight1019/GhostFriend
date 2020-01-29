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

        assertTrue(player.getCardList().contains(card1));
        assertTrue(player.getCardList().contains(card2));

        player.discardCard(card1);
        player.discardCard(card2);

        assertFalse(player.getCardList().contains(card1));
        assertFalse(player.getCardList().contains(card2));
    }

    @Test
    void declareContract() {
        player.declareContract(CardSuit.SPADE, 15);
        assertEquals("SPADE 15", player.getContract().toString());

        player.declareContract(null, 18);
        assertEquals("No giru 18", player.getContract().toString());
    }

    @Test
    void declareContract2() {
        Contract contract = new Contract();
        contract.declare(CardSuit.SPADE, 15);
        player.declareContract(contract);
        assertEquals("SPADE 15", player.getContract().toString());

        contract.declare(null, 15);
        player.declareContract(contract);
        assertEquals("No giru 15", player.getContract().toString());
    }
}