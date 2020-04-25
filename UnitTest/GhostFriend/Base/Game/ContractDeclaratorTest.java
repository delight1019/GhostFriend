package GhostFriend.Base.Game;

import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContractDeclaratorTest {
    private ContractDeclarator contractDeclarator;
    private List<Player> playerList;

    @BeforeAll
    public void createObjects() {
        playerList = new ArrayList<>();
        playerList.add(new Player("Tester1"));
        playerList.add(new Player("Tester2"));
        playerList.add(new Player("Tester3"));
        playerList.add(new Player("Tester4"));
        playerList.add(new Player("Tester5"));

        contractDeclarator = new ContractDeclarator(new Rule(), playerList);

        assertNull(contractDeclarator.getCurrentDeclarer());
    }

    @Test
    public void contractTest() {
        contractDeclarator.declare(playerList.get(0), CardSuit.SPADE, 14);
        assertEquals("SPADE 14", contractDeclarator.getCurrentContract(" "));
    }

}