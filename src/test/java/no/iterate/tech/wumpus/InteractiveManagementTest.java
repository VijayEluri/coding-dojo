package no.iterate.tech.wumpus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class InteractiveManagementTest {

	@Test
	public void printTurnNumberAndMaze() throws Exception {
		Game game = new Game();
		String response = game.process("P");
		assertEquals("Turn: 0\n###\n#@#\n###\n", response);
	}

	@Test
	public void quitQuits() throws Exception {
		Game game = new Game();
		String response = game.process("Q");
		assertFalse(game.running);
		assertEquals("Bye!", response);
	}

	@Test
	public void restRestsForATick() throws Exception {
		Game game = new Game();
		String response = game.process("R");
		assertEquals(1, game.turn);
		assertEquals("Zzz ...", response);
	}

}
