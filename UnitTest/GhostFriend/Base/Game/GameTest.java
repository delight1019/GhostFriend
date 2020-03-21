package GhostFriend.Base.Game;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    @Test
    void isAllPlayersEntered() throws FileNotFoundException {
        Game game = new Game();

        game.addPlayer("Tester1", new PrintWriter("Tester1.txt"));
        game.addPlayer("Tester2", new PrintWriter("Tester2.txt"));
        game.addPlayer("Tester3", new PrintWriter("Tester3.txt"));
        game.addPlayer("Tester4", new PrintWriter("Tester4.txt"));
        assertFalse(game.isAllPlayersEntered());

        game.addPlayer("Tester5", new PrintWriter("Tester5.txt"));
        assertTrue(game.isAllPlayersEntered());
    }

    @Test
    void getPlayersInfo() throws FileNotFoundException {
        Game game = new Game();

        game.addPlayer("Tester1", new PrintWriter("Tester1.txt"));
        game.addPlayer("Tester2", new PrintWriter("Tester2.txt"));
        game.addPlayer("Tester3", new PrintWriter("Tester3.txt"));
        game.addPlayer("Tester4", new PrintWriter("Tester4.txt"));
        game.addPlayer("Tester5", new PrintWriter("Tester5.txt"));

        //assertEquals("Tester1/Tester2/Tester3/Tester4/Tester5", game.getPlayersInfo("/"));
    }

    @Test
    void addPlayer() throws FileNotFoundException {
        Game game = new Game();

        assertEquals("Tester1", game.addPlayer("Tester1", new PrintWriter("Tester1.txt")).getName());
        assertEquals("Tester2", game.addPlayer("Tester2", new PrintWriter("Tester2.txt")).getName());
        assertEquals("Tester3", game.addPlayer("Tester3", new PrintWriter("Tester3.txt")).getName());
        assertEquals("Tester4", game.addPlayer("Tester4", new PrintWriter("Tester4.txt")).getName());
        assertEquals("Tester5", game.addPlayer("Tester5", new PrintWriter("Tester5.txt")).getName());
        assertNull(game.addPlayer("Tester6", new PrintWriter("Tester6.txt")));
    }
}