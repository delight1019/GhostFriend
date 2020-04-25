package GhostFriend.Base.Game;

import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;
import GhostFriend.Server.GameParams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContractDeclaratorTest {
    private ContractDeclarator contractDeclarator;
    private Rule rule;
    private List<Player> playerList;

    @BeforeEach
    public void createObjects() {
        rule = new Rule();

        playerList = new ArrayList<>();
        playerList.add(new Player("Tester1"));
        playerList.add(new Player("Tester2"));
        playerList.add(new Player("Tester3"));
        playerList.add(new Player("Tester4"));
        playerList.add(new Player("Tester5"));

        contractDeclarator = new ContractDeclarator(rule, playerList);
    }

    @Test
    @DisplayName("Base contract workflow")
    public void contractTest() {
        assertNull(contractDeclarator.getCurrentDeclarer());
        assertEquals(rule.getMinContractScore(), contractDeclarator.getMinContractScore());
        assertEquals(GameParams.NO_CONTRACT, contractDeclarator.getCurrentContract(" "));

        contractDeclarator.declare(playerList.get(0), CardSuit.SPADE, 14);
        assertEquals("SPADE 14", contractDeclarator.getCurrentContract(" "));
        assertEquals(15, contractDeclarator.getMinContractScore());
        assertEquals(playerList.get(0), contractDeclarator.getCurrentDeclarer());
        assertEquals(playerList.get(1), contractDeclarator.getDeclaringPlayer());
    }

    @Test
    @DisplayName("Determine declarer")
    public void determineDeclarer() {
        assertFalse(contractDeclarator.isFinished());

        contractDeclarator.passDeclaration(playerList.get(0));
        contractDeclarator.declare(playerList.get(1), CardSuit.SPADE, 14);
        contractDeclarator.passDeclaration(playerList.get(2));
        contractDeclarator.passDeclaration(playerList.get(3));
        contractDeclarator.passDeclaration(playerList.get(4));
        assertFalse(contractDeclarator.isFinished());

        contractDeclarator.passDeclaration(playerList.get(0));
        assertTrue(contractDeclarator.isFinished());
    }

    @Test
    @DisplayName("All pass")
    public void passAll() {
        int currentMinScore = contractDeclarator.getMinContractScore();

        contractDeclarator.passDeclaration(playerList.get(0));
        contractDeclarator.passDeclaration(playerList.get(1));
        contractDeclarator.passDeclaration(playerList.get(2));
        contractDeclarator.passDeclaration(playerList.get(3));
        assertEquals(currentMinScore, contractDeclarator.getMinContractScore());

        contractDeclarator.passDeclaration(playerList.get(4));

        assertEquals(currentMinScore - 1, contractDeclarator.getMinContractScore());
    }
}