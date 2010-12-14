package no.iterate.tech.wumpus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Test;

public class PitFallingTest {

	@Test
	public void playerDiesWhenGoingSouthIntoPit() throws Exception {
		Game game = new Game(1, 2);
		game.createPit(0, 1);
		assertTrue(game.goSouth());
		assertEquals(game.playerPosition, new Point(0, 1));
		assertTrue(game.over());
	}

	@Test
	public void playerDiesWhenGoingEastIntoPit() throws Exception {
		Game game = new Game(2, 1);
		game.createPit(1, 0);
		assertTrue(game.goEast());
		assertEquals(game.playerPosition, new Point(1, 0));
		assertTrue(game.over());
		assertTrue(game.messages.contains("You fell in a pit, game over ^^"));
	}
	
	

}
