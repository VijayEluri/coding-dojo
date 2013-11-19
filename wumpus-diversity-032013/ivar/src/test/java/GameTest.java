import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class GameTest {

    private Game game;
    private Room[][] gameBoard;

    @Before
    public void setUp() {
        game = new Game();
        gameBoard = game.getGameBoard();
    }

    @Test
    public void shootNorthWhenIntopNorthCornerWillEqualPlayerDead() throws Exception {
        game.setPlayerPosition(0,0);
        Player player = game.getPlayer();
        player.shoot('N');

        assertTrue(player.isDead());

    }

    @Test
    public void shootSouthWhenInNothCornerWillNotKillPlayer() throws Exception {
        game.setPlayerPosition(0,0);
        Player player = game.getPlayer();
        player.shoot('S');

        assertFalse(player.isDead());
    }

    @Test
    public void shootNorthWhenNotByNorthWallWillNotKillPlayer() throws Exception {
        game.setPlayerPosition(2,2);
        Player player = game.getPlayer();
        player.shoot('N');

        assertFalse(player.isDead());
    }

    @Test
    public void shootSouthWhenInSouthCornerWillEqualPlayerDead() throws Exception {
        game.setPlayerPosition(4,4);
        Player player = game.getPlayer();
        player.shoot('S');

        assertTrue(player.isDead());

    }

    @Test
    public void shootWestWhenNotByWestWallWillNotKillPlayer() throws Exception {
        game.setPlayerPosition(2,2);
        Player player = game.getPlayer();
        player.shoot('W');

        assertFalse(player.isDead());
    }


    @Test
    public void shootWestAndHittWumpus() throws Exception {
        game.setPlayerPosition(2, 2);
        Wumpus wumpus = game.setWumpusPosition(0, 2);

        Player player = game.getPlayer();
        player.shoot('W');

        assertFalse(player.isDead());
        assertTrue(wumpus.isDead());
    }
    @Test
    public void shootEastAndHittWumpus() throws Exception {
        game.setPlayerPosition(2, 2);
        Wumpus wumpus = game.setWumpusPosition(3, 2);

        Player player = game.getPlayer();
        player.shoot('E');

        assertFalse(player.isDead());
        assertTrue(wumpus.isDead());
    }
    @Test
    public void shootNorthAndHittWumpus() throws Exception {
        game.setPlayerPosition(2, 2);
        Wumpus wumpus = game.setWumpusPosition(2, 3);

        Player player = game.getPlayer();
        player.shoot('N');

        assertFalse(player.isDead());
        assertTrue(wumpus.isDead());
    }
    @Test
    public void shootSouthAndHittWumpus() throws Exception {
        game.setPlayerPosition(2, 2);
        Wumpus wumpus = game.setWumpusPosition(2, 1);

        Player player = game.getPlayer();
        player.shoot('S');

        assertFalse(player.isDead());
        assertTrue(wumpus.isDead());
    }

    @Test
    public void shootArrowThatHitsWall() throws Exception {


    }
}
