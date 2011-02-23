package no.iterate.tech.texasholdem;
import static org.junit.Assert.*;

import no.iterate.tech.texasholdem.Card;

import org.junit.Test;

public class CardTest {

	@Test
	public void testThat4cIsValidCard() throws Exception {
		assertNotNull(new Card("4c"));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenInvalidCardSpecified() throws Exception {
		new Card("sdf");
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowIllegalArgumentExceptionWhenCardSpecifiedWithOneLetter() throws Exception {
		new Card("1");
	}

	@Test
	public void testSuitIsCorrect() throws Exception {
		assertEquals("c", new Card("2c").getSuit());
	}
	
	
	@Test(expected = IllegalArgumentException.class)
	public void testSuitIsIncorrectWhenSuitisx() throws Exception {
		new Card("2x");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testSuitIsIncorrectWhenSuitIs7() throws Exception {
		new Card("27");
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowsIllegalArgumentExceptionWhenValueIsInvalid() throws Exception {
		new Card("1c");
	}

	
	@Test
	public void shouldGiveValidCardWhenValueIsKnight() throws Exception {
		assertEquals("J", new Card("Jd").getValue());
	}
	
	
	
//	@Test
//	public void shouldBeValidSuit() throws Exception {
//		assertFalse(Card.isCard(new Card("2a")));
//	}
}
