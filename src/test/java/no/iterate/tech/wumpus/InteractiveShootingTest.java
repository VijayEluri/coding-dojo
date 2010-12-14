package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class InteractiveShootingTest {

	@Test
	public void canShootEastFromMiddleOf3By3() throws Exception {
		Game game = new Game(3, 3);
		game.goEast();
		game.goSouth();
		String response = game.process("SE");
		assertEquals("Shooting east!", response);
		assertEquals(new Point(2, 1), game.arrowPosition);
	}

	@Test
	public void canShootWestFromMiddleOf3By3() throws Exception {
		Game game = new Game(3, 3);
		game.goEast();
		game.goSouth();
		String response = game.process("SW");
		assertEquals("Shooting west!", response);
		assertEquals(new Point(0, 1), game.arrowPosition);
	}

	@Test
	public void canShootNorthFromMiddleOf3By3() throws Exception {
		Game game = new Game(3, 3);
		game.goEast();
		game.goSouth();
		String response = game.process("SN");
		assertEquals("Shooting north!", response);
		assertEquals(new Point(1, 0), game.arrowPosition);
	}

	@Test
	public void canShootSouthFromMiddleOf3By3() throws Exception {
		Game game = new Game(3, 3);
		game.goEast();
		game.goSouth();
		String response = game.process("SS");
		assertEquals("Shooting south!", response);
		assertEquals(new Point(1, 2), game.arrowPosition);
	}
	
	@Test
	public void diesWhenShootingIntoWallNextToPlayer() throws Exception {
		Game game = new Game(3, 3);
		String response = game.process("SW");
		assertEquals("Shooting west!", response);
		assertTrue(game.over());
		assertTrue(game.messages.contains("Arrow bounced on wall. You die!"));
	}

}
