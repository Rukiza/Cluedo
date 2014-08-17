package cludo.game.cards;

import java.util.*;

public class Hand implements Iterable<Card>{
	
	List<Card> hand;
	
	/**
	 * Constrcts the hand with no card in it.
	 */
	public Hand(){
		hand = new ArrayList<Card>();
	}
	
	/**
	 * hand can recive cards.
	 * @param card - the card that is to be recived.
	 */
	public void recive(Card card){
		hand.add(card);
	}

	@Override
	public Iterator<Card> iterator() {
		return hand.iterator();
	}
	
}
