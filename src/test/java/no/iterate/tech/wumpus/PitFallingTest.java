package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

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
	
	@Test
	public void playerHearsWindWhenPitInTheEast() throws Exception {
		Game game = new Game(3, 1, new Point(0, 0));
		game.createPit(2, 0);
		assertTrue(game.messages.isEmpty());
		game.goEast();
		assertTrue(game.messages.contains("You hear wind"));
	}
	
	@Test
	public void playerHearsWindWhenPitInTheWest() throws Exception {
		Game game = new Game(3, 1, new Point(2, 0));
		game.createPit(0, 0);
		assertTrue(game.messages.isEmpty());
		game.goWest();
		assertTrue(game.messages.contains("You hear wind"));
	}
	
	@Test
	public void playerHearsWindWhenPitInTheNorth() throws Exception {
		Game game = new Game(1, 3, new Point(0, 2));
		game.createPit(0, 0);
		assertTrue(game.messages.isEmpty());
		game.goNorth();
		assertTrue(game.messages.contains("You hear wind"));
	}
	
	@Test
	public void playerHearsWindWhenPitInTheSouth() throws Exception {
		Game game = new Game(1, 3, new Point(0, 0));
		game.createPit(0, 2);
		assertTrue(game.messages.isEmpty());
		game.goSouth();
		assertTrue(game.messages.contains("You hear wind"));
	}

}
