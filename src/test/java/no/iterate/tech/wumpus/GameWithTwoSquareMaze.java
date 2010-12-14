package no.iterate.tech.wumpus;
import static org.junit.Assert.*;

import java.awt.Point;

import no.iterate.tech.wumpus.Game;

import org.junit.Test;

public class GameWithTwoSquareMaze {

	@Test
	public void playerCanMoveInOnlyOneDirection() throws Exception {
		// [ ] [ ]
		Game game = new Game(1, 2);
		assertTrue(game.goSouth());
		assertFalse(game.goEast());
	}

	@Test
	public void playerCannotGoThroughWall() throws Exception {
		Game game = new Game(1, 2);
		game.createWall(0, 1);
		assertFalse(game.goEast());
		assertEquals(game.playerPosition, new Point(0, 0));
	}

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
	}
	
	@Test
	public void arrowStaysInLastSquareBeforeHittingWallWhenShootingEast() throws Exception {
		Game game = new Game(2, 1);
		game.shootEast();
		assertEquals(game.playerPosition, new Point(0, 0));
		assertEquals(game.arrowPosition, new Point(1, 0));
		assertFalse(game.over());
	}
	@Test
	public void arrowStaysInLastSquareBeforeHittingWallWhenShootingSouth() throws Exception {
		Game game = new Game(1, 2);
		game.shootSouth();
		assertEquals(game.playerPosition, new Point(0, 0));
		assertEquals(game.arrowPosition, new Point(0, 1));
		assertFalse(game.over());
	}
	
	@Test
	public void youDieWhenYouShootIntoAWallNextToYou() throws Exception {
		Game game = new Game(2, 1);
		game.createWall(1, 0);
		assertFalse(game.over());
		game.shootEast();
		assertTrue(game.over());
	}

}
