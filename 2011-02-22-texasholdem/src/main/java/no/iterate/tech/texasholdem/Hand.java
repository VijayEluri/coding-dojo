package no.iterate.tech.texasholdem;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;


import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multiset;

public class Hand {

	private Set<Card> cards = new HashSet<Card>();

	public void add(Card card) {
		cards.add(card);
	}

	public String getValue() {
		if (isSameOfAKind(4)) {
			return "Four of a kind";
		} else if (isRoyalStreet()) {
			return "RoyalStraightFlush";
		} else if (isPair()) {
			return "Pair";
		}

		return "unknown";
	}

	String[] rsfSpades = { "As", "Ks", "Qs", "Js", "Ts" };
	String[] rsfClubs = { "Ac", "Kc", "Qc", "Jc", "Tc" };
	String[] rsfDiamonds = { "Ad", "Kd", "Qd", "Jd", "Td" };
	String[] rsfHearts = { "Ah", "Kh", "Qh", "Jh", "Th" };

	private boolean isRoyalStreet() {
		if (containsAll(rsfSpades) || containsAll(rsfClubs)
				|| containsAll(rsfDiamonds) || containsAll(rsfHearts)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean containsAll(String[] rsf) {
		for (String expectedCard : rsf) {
			if (!cards.contains(new Card(expectedCard))) {
				return false;
			}
		}
		return true;
	}

	private boolean isPair() {
		return isSameOfAKind(2);
	}
	
	private boolean isSameOfAKind(int sameCount) {
		Multimap<String, Card> handMap = ArrayListMultimap.create();		
		for (Card currentCard : cards) {
			handMap.put(currentCard.getValue(), currentCard);
		}

		Multiset<String> keys = handMap.keys();
		for (String cardValue : keys) {
			Collection<Card> collection = handMap.get(cardValue);
			if(collection.size() == sameCount) {
				return true;
			}
		}
		return false;
	}

}
