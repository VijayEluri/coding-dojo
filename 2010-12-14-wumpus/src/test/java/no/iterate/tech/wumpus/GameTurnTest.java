package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import no.iterate.tech.wumpus.Game;

import org.junit.Test;

public class GameTurnTest {

	@Test
	public void shootingWillMakeGameTick() throws Exception {
		Game game = new Game();
		game.shootEast();
		assertEquals(1, game.turn);
	}

	@Test
	public void tryingToMoveIntoAWallWillNotMakeTheGameTick() throws Exception {
		Game game = new Game();
		game.goEast();
		assertEquals(0, game.turn);
	}

	@Test
	public void movingInAValidDirectionWillMakeTheGameTick() throws Exception {
		Game game = new Game(2, 1);
		game.goEast();
		assertEquals(1, game.turn);
	}

}
