package no.iterate.tech.wumpus;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class InteractiveManagementTest {

	@Test
	public void printTurnNumberAndInventoryAndMaze() throws Exception {
		Game game = new Game();
		String response = game.process("P");
		String[] lines = response.split("\n");
		assertEquals(4, lines.length);
		assertEquals("Turn: 0,  Inventory: +", lines[0]);
		assertEquals("###", lines[1]);
		assertEquals("#@#", lines[2]);
		assertEquals("###", lines[3]);
	}
	
	@Test
	public void printTurnNumberAndEmptyInventory() throws Exception {
		Game game = new Game(2, 1);
		game.shootEast();
		String response = game.process("P");
		String[] lines = response.split("\n");
		assertEquals(4, lines.length);
		assertEquals("Turn: 1,  Inventory: empty", lines[0]);
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
