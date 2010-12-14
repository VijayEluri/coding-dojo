package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class WumpusTest {

	@Test
	public void wumpusIsCreatedAtBottomRightPoint() throws Exception {
		Game game = new Game(3, 3);
		game.addWumpus(new Point(2, 2));
		assertEquals(new Point(2, 2), game.wumpusPosition);
	}

	@Test
	public void wumpusDiesWhenArrowIsShotAtIt() throws Exception {
		Game game = new Game(3, 1);
		game.addWumpus(new Point(2, 0));
		assertTrue(game.messages.isEmpty());
		game.shootEast();
		assertTrue(game.messages.contains("You shot the Wumpus! You WIN!"));
		assertFalse(game.running);
	}

}
