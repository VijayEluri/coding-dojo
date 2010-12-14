package no.iterate.tech.wumpus;
import static org.junit.Assert.*;

import no.iterate.tech.wumpus.Game;

import org.junit.Test;


public class MazePrinterTest {

	@Test
	public void printMazeWithPlayerAndWallsAndPit() throws Exception {
		Game game = new Game(3, 3);
		game.createWall(1, 1);
		game.createWall(0, 2);
		game.createWall(1, 2);
		game.createPit(2, 2);
		
		assertEquals("#####\n#@  #\n# x #\n#xxo#\n#####\n", game.printMaze());
	}
	
}
