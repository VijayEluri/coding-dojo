import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class WumpusTest {

    @Test
    public void loosesIfBumpsIntoWumpus() throws Exception {
        Game game = new Game();
        game.setWumpus(0, 0);
        game.setHero(0, 0);
        assertEquals("You lost, sucker!", game.getStatus());
    }

    @Test
    public void testNotLoseGame() throws Exception {
        Game game = new Game();
        game.setWumpus(1, 1);
        game.setHero(0, 0);
        assertEquals("Game on!", game.getStatus());
    }

    @Test
    public void winsWhenWumpusDeadPoorThing() throws Exception {
        Game game = new Game();
        game.setWumpusDead();
        assertEquals("You won, you evil kitten killer!", game.getStatus());
    }
}
