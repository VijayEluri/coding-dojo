package no.iterate.tech.wumpus;
import static org.junit.Assert.*;

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
	public void playerShootsArrowThenMovesAfterItAndThenPicksItUp() throws Exception {
		game.shootEast();
		game.goEast();
		assertFalse(game.playerHasArrow());
		game.goEast();
		assertTrue(game.playerHasArrow());
	}
	
}
