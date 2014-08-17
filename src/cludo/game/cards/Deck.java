package cludo.game.cards;


import java.util.*;

/**
 * Deck of Unique Cards
 * @author Shane Brewer.
 *
 */
public class Deck {
	
	// Deck that contains all the cards in the game.
	private Queue<Card> deck;
	
	/**
	 * Constructs a deck shuffling the cards.
	 */
	public Deck(){
		Card[] deck = { new Card(Card.Type.WEAPON, "Dagger"),
				new Card(Card.Type.WEAPON, "Candlestick"),
				new Card(Card.Type.WEAPON, "Leadpipe"),
				new Card(Card.Type.WEAPON, "Revolver"),
				new Card(Card.Type.WEAPON, "Rope"),
				new Card(Card.Type.WEAPON, "Spanner"),
				new Card(Card.Type.CHARACTER, "MissScarlett"),
				new Card(Card.Type.CHARACTER, "ProfessorPlum"),
				new Card(Card.Type.CHARACTER, "MrsPeacock"),
				new Card(Card.Type.CHARACTER, "ReverandGreen"),
				new Card(Card.Type.CHARACTER, "ColonelMustard"),
				new Card(Card.Type.CHARACTER, "MrsWhite"),
				new Card(Card.Type.ROOM, "Kitchen"),
				new Card(Card.Type.ROOM, "Ballroom"),
				new Card(Card.Type.ROOM, "Conservatory"),
				new Card(Card.Type.ROOM, "BilliardRoom"),
				new Card(Card.Type.ROOM, "Library"),
				new Card(Card.Type.ROOM, "Study"),
				new Card(Card.Type.ROOM, "Lounge"),
				new Card(Card.Type.ROOM, "Hall"),
				new Card(Card.Type.ROOM, "DiningRoom") };
		ArrayList<Card> shuffle = new ArrayList<Card>();
		for (Card c: deck){
			shuffle.add(c);
		}
		Collections.shuffle(shuffle);
		this.deck = new LinkedList<Card>(shuffle);
	}
	
	/**
	 * Deals out a card from the deck.
	 * @return - card from the top of the deck.
	 */
	public Card dealCard(){
		return deck.poll();
	}
	
	/**
	 * Places a card back onto the bottom of the deck.
	 * @param card - card to be added.
	 */
	public void placeCard(Card card){
		deck.offer(card);
	}
	
	/**
	 * checks if the deck is empty.
	 * @return - true if the deck is empty.
	 */
	public boolean isEmpty(){
		return deck.isEmpty();
	}
}

