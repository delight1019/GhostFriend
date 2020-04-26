package GhostFriend.Base.Game;

import GhostFriend.Base.Card.Card;
import GhostFriend.Base.Card.CardSuit;
import GhostFriend.Base.Card.CardValue;
import GhostFriend.Base.Player.Player;
import GhostFriend.Base.Rule.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GameControllerTest {
    private GameController gameController;
    private List<Player> playerList;
    private Rule rule;
    private Player declarer;
    private Player friend;

    @BeforeEach
    public void createObjects() {
        rule = new Rule();
        rule.setGiru(CardSuit.SPADE);

        playerList = new ArrayList<>();
        playerList.add(new Player("Tester1"));
        playerList.add(new Player("Tester2"));
        playerList.add(new Player("Tester3"));
        playerList.add(new Player("Tester4"));
        playerList.add(new Player("Tester5"));

        declarer = playerList.get(0);
        friend = playerList.get(1);

        playerList.get(0).receiveCard(new Card(CardSuit.JOKER, CardValue.JOKER));
        playerList.get(0).receiveCard(new Card(CardSuit.SPADE, CardValue.THREE));

        playerList.get(1).receiveCard(new Card(CardSuit.HEART, CardValue.JACK));
        playerList.get(1).receiveCard(new Card(CardSuit.HEART, CardValue.QUEEN));

        playerList.get(2).receiveCard(new Card(CardSuit.CLUB, CardValue.JACK));
        playerList.get(2).receiveCard(new Card(CardSuit.CLUB, CardValue.QUEEN));

        playerList.get(3).receiveCard(new Card(CardSuit.DIAMOND, CardValue.JACK));
        playerList.get(3).receiveCard(new Card(CardSuit.DIAMOND, CardValue.QUEEN));

        playerList.get(4).receiveCard(new Card(CardSuit.SPADE, CardValue.JACK));
        playerList.get(4).receiveCard(new Card(CardSuit.SPADE, CardValue.QUEEN));

        gameController = new GameController(playerList, rule, declarer, friend);
    }

    @Test
    public void initialCondition() {
        assertEquals(declarer, gameController.getCurrentPlayer());
        assertEquals(declarer, gameController.getWinner());
    }

    @Test
    @DisplayName("One Phase Submission")
    public void submission() {
        gameController.submitCard(declarer, new Card(CardSuit.SPADE, CardValue.JACK));
        assertEquals(playerList.get(1), gameController.getCurrentPlayer());
        assertEquals(declarer, gameController.getWinner());

        gameController.submitCard(gameController.getCurrentPlayer(), new Card(CardSuit.SPADE, CardValue.KING));
        assertEquals(playerList.get(1), gameController.getWinner());
        assertFalse(gameController.isPhaseFinished());

        gameController.submitCard(gameController.getCurrentPlayer(), new Card(CardSuit.HEART, CardValue.KING));
        gameController.submitCard(gameController.getCurrentPlayer(), new Card(CardSuit.DIAMOND, CardValue.KING));
        gameController.submitCard(gameController.getCurrentPlayer(), new Card(CardSuit.CLUB, CardValue.KING));

        assertEquals(playerList.get(0), gameController.getCurrentPlayer());
        assertEquals(5, gameController.getPhaseScore());
        assertTrue(gameController.isPhaseFinished());

        gameController.clearPhase();

        assertEquals(playerList.get(1), gameController.getCurrentPlayer());
        assertEquals(0, gameController.getPhaseScore());
    }

    @Test
    @DisplayName("All Phase Submission")
    public void allPhaseSubmission() {
        assertFalse(gameController.isAllPhaseFinished());

        gameController.submitCard(playerList.get(0), playerList.get(0).getCardList().get(0));
        gameController.submitCard(playerList.get(1), playerList.get(1).getCardList().get(0));
        gameController.submitCard(playerList.get(2), playerList.get(2).getCardList().get(0));
        gameController.submitCard(playerList.get(3), playerList.get(3).getCardList().get(0));
        gameController.submitCard(playerList.get(4), playerList.get(4).getCardList().get(0));

        gameController.submitCard(playerList.get(0), playerList.get(0).getCardList().get(0));
        gameController.submitCard(playerList.get(1), playerList.get(1).getCardList().get(0));
        gameController.submitCard(playerList.get(2), playerList.get(2).getCardList().get(0));
        gameController.submitCard(playerList.get(3), playerList.get(3).getCardList().get(0));
        gameController.submitCard(playerList.get(4), playerList.get(4).getCardList().get(0));

        assertTrue(gameController.isAllPhaseFinished());
    }
}