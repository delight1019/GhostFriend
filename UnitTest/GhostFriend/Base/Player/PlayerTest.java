package GhostFriend.Base.Player;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
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
}