package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Rule.Contract;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerTest {
    private Player player = new Player("testPlayer");

    @Test
    void getName() {
        assertEquals("testPlayer", player.getName());
    }

    @Test
    void receiveCard() {
        player.receiveCard(new Card(CardSuit.JOKER, CardValue.JOKER));
        player.receiveCard(new Card(CardSuit.CLUB, CardValue.SEVEN));

        assertEquals(2, player.getCardList().size());
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