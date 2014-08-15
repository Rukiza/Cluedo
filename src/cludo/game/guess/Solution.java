package cludo.game.guess;

import cludo.game.cards.*;

public class Solution {
	private Card murderRoom;
	private Card murderCharacter;
	private Card murderWeapon;
	
	/**
	 * For testing perposes 
	 * @param murderRoom 
	 * @param murderCharacter
	 * @param murderWeapon
	 */
	public Solution(Card murderRoom, Card murderCharacter, Card murderWeapon) {
		super();
		this.murderRoom = murderRoom;
		this.murderCharacter = murderCharacter;
		this.murderWeapon = murderWeapon;
	}

	/**
	 * Takes a deck and produces a valid Solution.
	 * @param deck
	 */
	public Solution(Deck deck) {
		while (murderRoom == null || murderCharacter == null
				|| murderWeapon == null) {
			Card c = deck.dealCard();
			if (murderRoom == null && c.checkType(Card.Type.ROOM)) {
				murderRoom = c;
			} else if (murderCharacter == null
					&& c.checkType(Card.Type.CHARACTER)) {
				murderCharacter = c;
			} else if (murderWeapon == null && c.checkType(Card.Type.WEAPON)) {
				murderWeapon = c;
			} else {
				deck.placeCard(c);
			}
		}
	}

	/**
	 * Checks if an accusation matches the solution
	 * Requires accuse not to equal null;
	 * @param accuse - the accusation to match with the solution
	 * @return	- true if they match else false;
	 */
	public boolean checkAccuse(Accuse accuse) {
		if (accuse.getMurderWeapon().equals(murderWeapon)
				&& accuse.getMurderRoom().equals(murderRoom)
				&& accuse.getMurderCharacter().equals(murderCharacter)) {
			return true;
		}
		return false;
	}

}
