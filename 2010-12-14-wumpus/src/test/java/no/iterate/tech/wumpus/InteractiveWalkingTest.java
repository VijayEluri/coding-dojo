package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import org.junit.Test;

public class InteractiveWalkingTest {

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
	public void printHelpIfWeDoNotUnderstandTheCommand() throws Exception {
		Game game = new Game();
		String response = game.process("lsdad");
		assertEquals("Can't understand you. Try ([S]{E,W,N,S}|R|P|Q) ;-)",
				response);
	}

}
