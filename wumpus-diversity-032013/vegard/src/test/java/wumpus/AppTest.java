package wumpus;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.junit.*;
import wumpus.user.Player;

import static org.junit.Assert.*;

/**
 * Unit test for simple Game.
 */
public class AppTest {

private Game game;

        @Before
        public void setUp() {
            game = new Game();
        }

        @org.junit.Test
        public void test_moveEast() throws Exception {
            assertEquals(StatusCode.OK, game.movePlayer(Direction.E).getStatusCode());
        }


        @org.junit.Test
        public void test_moveWest() throws Exception {
            assertFalse(game.isPlayerMoveValid(Direction.W));
        }

        @org.junit.Test
        public void test_moveSouth() throws Exception {
            assertTrue(game.isPlayerMoveValid(Direction.S));
        }

        @org.junit.Test
        public void test_moveNorth() throws Exception {
            assertFalse(game.isPlayerMoveValid(Direction.N));
        }

        @org.junit.Test
        public void testPlayerHitsWall() {
            assertEquals(StatusCode.WALL, game.movePlayer(Direction.W).getStatusCode());
        }

    @org.junit.Test
    public void testWumpusEatsComingPlayer() {
        game.placeWumpus(0, 1);
        assertEquals(StatusCode.DEAD, game.movePlayer(Direction.S).getStatusCode());
    }

}
