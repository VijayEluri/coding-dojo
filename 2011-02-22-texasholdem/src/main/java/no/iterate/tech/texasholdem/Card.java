package no.iterate.tech.texasholdem;
import java.util.HashSet;
import java.util.Set;

public class Card {
	private String value;
	private String suit;
	
	private static Set<String> values= new HashSet<String>();
	private static Set<String> suits = new HashSet<String>();

	static
	{
		suits.add("c");
		suits.add("h");
		suits.add("s");
		suits.add("d");
		
		values.add("2");
		values.add("3");
		values.add("4");
		values.add("5");
		values.add("6");
		values.add("7");
		values.add("8");
		values.add("9");
		values.add("T");
		values.add("J");
		values.add("Q");
		values.add("K");
		values.add("A");
	};

	public Card(String string) {

		if (string.length() != 2) {
			throw new IllegalArgumentException("Invalid lenght of the card");
		}

		value = string.substring(0, 1);
		suit = string.substring(1, 2);

		if (!suits.contains(suit)) {
			throw new IllegalArgumentException("Invalid suit of the card");
		}
		
		if(!values.contains(value)) {
			throw new IllegalArgumentException("Invalid value of the card");
		}
	}

	public String getSuit() {
		return suit;
	}

	public String getValue() {
		return value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (suit == null) {
			if (other.suit != null)
				return false;
		} else if (!suit.equals(other.suit))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

}
