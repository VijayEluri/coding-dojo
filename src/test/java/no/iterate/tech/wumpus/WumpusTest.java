package no.iterate.tech.wumpus;

import static org.junit.Assert.*;

import java.awt.Point;

import org.junit.Test;

public class WumpusTest {

	@Test
	public void wumpusIsCreatedAtBottomRightPoint() throws Exception {
		Game game = new Game(3,3);
		game.addWumpus(new Point(2,2));
		assertEquals(new Point(2,2), game.wumpusPosition);
	}
}
