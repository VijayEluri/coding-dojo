package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class InteractiveGameTest {

	@Test
	public void cannotGoEastIfWallInEast() throws Exception {
		Game game = new Game();
		String response = game.process("E");
		assertEquals("You can't go east from here", response);
	}

	@Test
	public void cannotGoSouthIfWallInSouth() throws Exception {
		Game game = new Game();
		String response = game.process("S");
		assertEquals("You can't go south from here", response);
	}

	@Test
	public void cannotGoWestIfWallInWest() throws Exception {
		Game game = new Game();
		String response = game.process("W");
		assertEquals("You can't go west from here", response);
	}

	@Test
	public void cannotGoNorthIfWallInNorth() throws Exception {
		Game game = new Game();
		String response = game.process("N");
		assertEquals("You can't go north from here", response);
	}

	@Test
	public void canGoEastIfPathToTheEast() throws Exception {
		Game game = new Game(2, 1);
		String response = game.process("E");
		assertEquals("Moving east", response);
	}

	@Test
	public void canGoWastIfPathToTheWest() throws Exception {
		Game game = new Game(2, 1);
		game.goEast();
		String response = game.process("W");
		assertEquals("Moving west", response);
	}

	@Test
	public void canGoSouthIfPathToTheSouth() throws Exception {
		Game game = new Game(1, 2);
		String response = game.process("S");
		assertEquals("Moving south", response);
	}

	@Test
	public void canGoNorthIfPathToTheNorth() throws Exception {
		Game game = new Game(1, 2);
		game.goSouth();
		String response = game.process("N");
		assertEquals("Moving north", response);
	}

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
	public void printHelpIfWeDoNotUnderstandTheCommand() throws Exception {
		Game game = new Game();
		String response = game.process("lsdad");
		assertEquals("Can't understand you. Try ([S]{E,W,N,S}|R|P|Q) ;-)",
				response);
	}

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
