package no.iterate.tech.texasholdem;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import no.iterate.tech.texasholdem.Card;
import no.iterate.tech.texasholdem.Hand;

import org.junit.Test;

public class HandTest {

	private Hand hand = new Hand();

	@Test
	public void testHandIsPair() throws Exception {

		hand.add(new Card("2c"));
		hand.add(new Card("2d"));

		assertEquals("Pair", hand.getValue());

	}

	@Test
	public void testHandIsNotPair() throws Exception {

		hand.add(new Card("3c"));
		hand.add(new Card("2d"));

		assertFalse("Pair".equals(hand.getValue()));
	}

	@Test
	public void isRoyalStraightFlush() throws Exception {
		hand.add(new Card("As"));
		hand.add(new Card("Ks"));
		hand.add(new Card("Qs"));
		hand.add(new Card("Js"));
		hand.add(new Card("Ts"));
		
		assertEquals("RoyalStraightFlush", hand.getValue());
	}

	@Test
	public void isRoyalStraightFlushwith7Cards() throws Exception {
		hand.add(new Card("As"));
		hand.add(new Card("Ks"));
		hand.add(new Card("Qs"));
		hand.add(new Card("Js"));
		hand.add(new Card("Ts"));
		hand.add(new Card("2d"));
		hand.add(new Card("9h"));
		
		assertEquals("RoyalStraightFlush", hand.getValue());
	}
	
	@Test
	public void isRoyalStraightFlushWithClubs() throws Exception {
		hand.add(new Card("Ac"));
		hand.add(new Card("Kc"));
		hand.add(new Card("Qc"));
		hand.add(new Card("Jc"));
		hand.add(new Card("Tc"));
		
		assertEquals("RoyalStraightFlush", hand.getValue());
		
	}
	
	@Test
	public void isNotRoyalStraightFlush() throws Exception {
		hand.add(new Card("As"));
		hand.add(new Card("Ks"));
		hand.add(new Card("Qs"));
		hand.add(new Card("Ts"));
		hand.add(new Card("9s"));
		hand.add(new Card("8s"));
		
		assertThat(hand.getValue(), not(equalTo("RoyalStraightFlush")));
	}
	
	@Test
	public void isFourOfAKind() throws Exception {
		hand.add(new Card("Ad"));
		hand.add(new Card("As"));
		hand.add(new Card("Ah"));
		hand.add(new Card("Ac"));
		
		assertEquals("Four of a kind", hand.getValue());
	}
	
	@Test
	public void foo() throws Exception {
		hand.add(new Card("Ad"));
		hand.add(new Card("Qd"));
		hand.add(new Card("Qh"));
		hand.add(new Card("Qc"));
		hand.add(new Card("Qs"));
		hand.add(new Card("As"));
		
		assertEquals("Four of a kind", hand.getValue());
	}
	
	
}
