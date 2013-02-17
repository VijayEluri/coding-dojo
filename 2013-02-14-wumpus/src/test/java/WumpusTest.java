import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.*;
import static org.fest.assertions.api.Assertions.assertThat;

public class WumpusTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(5);
    }

    @Test
    public void loosesIfBumpsIntoWumpus() throws Exception {
        game.setWumpus(0, 0);
        game.setHero(0, 0);
        assertEquals("You lost, sucker!", game.getStatus());
    }

    @Test
    public void loosesIfBumpsIntoWumpusTwo() throws Exception {
        game.setWumpus(1, 1);
        game.setHero(1, 1);
        assertEquals("You lost, sucker!", game.getStatus());
    }

    @Test
    public void testNotLoseGame() throws Exception {
        game.setWumpus(1, 1);
        game.setHero(0, 0);
        assertEquals("Game on!", game.getStatus());
    }

    @Test
    public void winsWhenWumpusDeadPoorThing() throws Exception {
        game.setWumpusDead();
        assertEquals("You won, you evil kitten killer!", game.getStatus());
    }

    @Test
    public void moveHeroNorth() throws Exception {
        game.setHero(0, 0);
        game.moveHero(Direction.NORTH);

        assertEquals(0, game.getHeroX());
        assertEquals(1, game.getHeroY());
    }

    @Test
    public void moveHeroSouth() throws Exception {
        game.setHero(1, 1);
        game.moveHero(Direction.SOUTH);

        assertEquals(1, game.getHeroX());
        assertEquals(0, game.getHeroY());
    }

    @Test
    public void moveHeroWest() throws Exception {
        game.setHero(1, 1);
        game.moveHero(Direction.WEST);

        assertEquals(0, game.getHeroX());
        assertEquals(1, game.getHeroY());
    }

    @Test
    public void moveHeroEast() throws Exception {
        game.setHero(1, 1);
        game.moveHero(Direction.EAST);

        assertEquals(2, game.getHeroX());
        assertEquals(1, game.getHeroY());
    }

    @Test
    public void cantMoveSouthWhenOutOfBoard() throws Exception {
        Game game = new Game(1);
        game.setHero(0,0);

        assertEquals("Can't move south from here", game.moveHero(Direction.SOUTH));
        assertThat(game.getHero()).isEqualTo(pos(0, 0));
    }

    @Test
    public void cantMoveNorthWhenOutOfBoard() throws Exception {
        Game game = new Game(1);
        game.setHero(0,0);

        assertEquals("Can't move north from here", game.moveHero(Direction.NORTH));
        assertThat(game.getHero()).isEqualTo(pos(0, 0));
    }

    private static int[] pos(int x, int y) {
        return new int[]{x, y};
    }
}
