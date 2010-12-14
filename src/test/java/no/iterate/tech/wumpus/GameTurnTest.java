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

}
