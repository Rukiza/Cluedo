package cludo.game.guess;

import cludo.game.cards.Card;

public class Accuse {
	private Card murderRoom;
	private Card murderCharacter;
	private Card murderWeapon;
	
	/**
	 * Constructs an accuseation 
	 * @param murderRoom - this must be a room card.
	 * @param murderCharacter - this must be a character card.
	 * @param murderWeapon - this must ba a weapon card.
	 */
	public Accuse(Card murderRoom, Card murderCharacter, Card murderWeapon) {
		super();
		this.murderRoom = murderRoom;
		this.murderCharacter = murderCharacter;
		this.murderWeapon = murderWeapon;
	}

	/**
	 * returns the room card.
	 * @return
	 */
	public Card getMurderRoom() {
		return murderRoom;
	}

	/**
	 * @return - returns the character card associated with the accusation.
	 */
	public Card getMurderCharacter() {
		return murderCharacter;
	}

	/**
	 * @return - returns the weapon card assoceated with the accusation.
	 */
	public Card getMurderWeapon() {
		return murderWeapon;
	}
	
	
}
