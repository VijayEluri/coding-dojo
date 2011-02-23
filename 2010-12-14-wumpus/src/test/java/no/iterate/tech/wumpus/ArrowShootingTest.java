package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import java.awt.Point;

import no.iterate.tech.wumpus.Game;

import org.junit.Before;
import org.junit.Test;

public class ArrowShootingTest {

	Game game;

	@Before
	public void setUp() {
		game = new Game(3, 3);
	}

	@Test
	public void playerHasArrowWhenGameStarts() throws Exception {
		assertTrue(game.playerHasArrow());
	}

	@Test
	public void playerDoesNotHaveArrowAfterShootingIt() throws Exception {
		game.shootEast();
		assertFalse(game.playerHasArrow());
	}

	@Test
	public void playerShootsArrowThenMovesAfterItAndThenPicksItUp()
			throws Exception {
		game.shootEast();
		game.goEast();
		assertFalse(game.playerHasArrow());
		game.goEast();
		assertTrue(game.playerHasArrow());
		assertTrue(game.messages.contains("You picked up the arrow"));
	}

	@Test
	public void arrowStaysInLastSquareBeforeHittingWallWhenShootingEast()
			throws Exception {
		Game game = new Game(2, 1);
		game.shootEast();
		assertEquals(game.playerPosition, new Point(0, 0));
		assertEquals(game.arrowPosition, new Point(1, 0));
		assertFalse(game.over());
	}

	@Test
	public void arrowStaysInLastSquareBeforeHittingWallWhenShootingSouth()
			throws Exception {
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
	
	@Test
	public void arrowStaysInLastSquareBeforeHittingWallWhenShootingWest()
			throws Exception {
		Game game = new Game(3, 3);
		game.goEast();
		game.shootWest();
		assertEquals(game.playerPosition, new Point(1, 0));
		assertEquals(game.arrowPosition, new Point(0, 0));
		assertFalse(game.over());
	}
	
	@Test
	public void cantShootArrowIfYouDontHaveArrow() throws Exception {
		Game game = new Game(3, 3);
		assertTrue(game.shootEast());
		assertFalse(game.shootEast());
		
	}
	

}
