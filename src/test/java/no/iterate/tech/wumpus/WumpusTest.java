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
	}

	@Test
	public void youSmellTheWumpusWhenWalkingEastIntoIt() throws Exception {
		Game game = new Game(3, 1, new Point(0, 0));
		game.addWumpus(new Point(2, 0));
		assertTrue(game.messages.isEmpty());
		game.goEast();
		assertTrue(game.messages.contains("You smell the Wumpus"));
	}

	@Test
	public void youSmellTheWumpusWhenWalkingWestIntoIt() throws Exception {
		Game game = new Game(3, 1, new Point(2, 0));
		game.addWumpus(new Point(0, 0));
		assertTrue(game.messages.isEmpty());
		game.goWest();
		assertTrue(game.messages.contains("You smell the Wumpus"));
	}

	@Test
	public void youSmellTheWumpusWhenWalkingNorthIntoIt() throws Exception {
		Game game = new Game(1, 3, new Point(0, 2));
		game.addWumpus(new Point(0, 0));
		assertTrue(game.messages.isEmpty());
		game.goNorth();
		assertTrue(game.messages.contains("You smell the Wumpus"));
	}

	@Test
	public void youSmellTheWumpusWhenWalkingSouthIntoIt() throws Exception {
		Game game = new Game(1, 3, new Point(0, 0));
		game.addWumpus(new Point(0, 2));
		assertTrue(game.messages.isEmpty());
		game.goSouth();
		assertTrue(game.messages.contains("You smell the Wumpus"));
	}

}
