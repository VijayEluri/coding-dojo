package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import java.awt.Point;

import no.iterate.tech.wumpus.Game;

import org.junit.Test;

public class MazeDisplayTest {

	@Test
	public void withPlayerAndWallsAndPit() throws Exception {
		Game game = new Game(3, 3);
		game.createWall(1, 1);
		game.createWall(0, 2);
		game.createWall(1, 2);
		game.createPit(2, 2);

		assertEquals("#####\n#@  #\n# x #\n#xxo#\n#####\n", game.getMazeDisplay());
	}
	
	@Test
	public void withPlayerAndWallsAndPitAndArrow() throws Exception {
		Game game = new Game(3, 3);
		game.createWall(1, 1);
		game.createWall(0, 2);
		game.createWall(1, 2);
		game.createPit(2, 2);
		game.shootEast();

		assertEquals("#####\n#@ +#\n# x #\n#xxo#\n#####\n", game.getMazeDisplay());
	}
	
	@Test
	public void withPlayerAndWallsAndPitAndWumpus() throws Exception {
		Game game = new Game(3, 3);
		game.createWall(1, 1);
		game.createWall(0, 2);
		game.createWall(1, 2);
		game.createPit(2, 2);
		game.addWumpus(new Point(2, 0));
		
		assertEquals("#####\n#@ W#\n# x #\n#xxo#\n#####\n", game.getMazeDisplay());
	}

}
