package no.iterate.tech.wumpus;
import static org.junit.Assert.*;

import no.iterate.tech.wumpus.Game;

import org.junit.Test;

public class GameWithSingleSquareMaze {

	@Test
	public void cantGoDirection() throws Exception {
		Game game = new Game();
		assertFalse(game.goNorth());
		assertFalse(game.goEast());
		assertFalse(game.goWest());
		assertFalse(game.goSouth());
	}
	
	@Test
	public void youDieWhenYouShootNorth() throws Exception {
		Game game = new Game();
		assertFalse(game.over());
		game.shootNorth();
		assertTrue(game.over());
	}
	
	@Test
	public void youDieWhenYouShootEast() throws Exception {
		Game game = new Game();
		assertFalse(game.over());
		game.shootEast();
		assertTrue(game.over());
	}
	
	@Test
	public void youDieWhenYouShootWest() throws Exception {
		Game game = new Game();
		assertFalse(game.over());
		game.shootWest();
		assertTrue(game.over());
	}
	
	@Test
	public void youDieWhenYouShootSouth() throws Exception {
		Game game = new Game();
		assertFalse(game.over());
		game.shootSouth();
		assertTrue(game.over());
	}
	
	@Test
	public void restingWillMakeTheGameTickOnce() throws Exception {
		Game game = new Game();
		assertEquals(0, game.turn);
		game.rest();
		assertEquals(1, game.turn);
	}
	
}
