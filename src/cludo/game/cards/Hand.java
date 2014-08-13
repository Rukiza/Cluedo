package cludo.game.cards;

import java.util.*;

public class Hand implements Iterable<Card>{
	
	List<Card> hand;
	
	public Hand(){
		hand = new ArrayList<Card>();
	}
	
	public void recive(Card card){
		hand.add(card);
	}

	@Override
	public Iterator<Card> iterator() {
		return hand.iterator();
	}
	
}
