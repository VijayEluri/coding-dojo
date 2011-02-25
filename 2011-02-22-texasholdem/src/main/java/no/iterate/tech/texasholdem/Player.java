package no.iterate.tech.texasholdem;

import java.util.HashSet;
import java.util.Set;

public class Player {
	
	private String name;
	private Set<Hand> hands;
	
	public Player(String name){
		this.name = name;
		
		hands = new HashSet<Hand>();
		hands.add(new Hand());
		hands.add(new Hand());
	}

	public String getName() {
		return name;
	}

	public void addHand(Hand hand) throws IndexOutOfBoundsException {
		if(hands.size() > 7)
			throw new IndexOutOfBoundsException("Player can only have 7 cards on a Hand");
			
        hands.add(hand);
	}

	public Set<Hand> getCurrentHands() {
		return hands;
	}
}
