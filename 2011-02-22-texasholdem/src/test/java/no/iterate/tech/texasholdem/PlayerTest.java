package no.iterate.tech.texasholdem;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

	private Player player;
	@Before
	public void setUp(){
		player = new Player("Four Fingers");
	}
	
	@After
	public void tearDown(){
		player = null;
	}
	
	@Test
	public void playerStartsWithTwoCards(){		
		assertNotNull(player.getCurrentHands());
		assertEquals(2, player.getCurrentHands().size());
	}
	
	@Test (expected = IndexOutOfBoundsException.class)
	public void shouldThrowExceptionWhenAddingMoreThanSevenCardsOnTheHand() throws Exception{
		player.addHand(new Hand());
		player.addHand(new Hand());
		player.addHand(new Hand());
		player.addHand(new Hand());
		player.addHand(new Hand());
		player.addHand(new Hand());				
		player.addHand(new Hand());
	}
}
