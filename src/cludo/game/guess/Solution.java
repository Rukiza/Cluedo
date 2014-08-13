package cludo.game.guess;

import cludo.game.cards.*;

public class Solution {
	private Card murderRoom;
	private Card murderCharacter;
	private Card murderWeapon;
	
	public Solution(Card murderRoom, Card murderCharacter, Card murderWeapon) {
		super();
		this.murderRoom = murderRoom;
		this.murderCharacter = murderCharacter;
		this.murderWeapon = murderWeapon;
	}
	
	/**
	 * Takes a deck and produsees a valid Solution.
	 * @param deck
	 */
	public Solution(Deck deck){
		while (murderRoom == null || murderCharacter == null || murderWeapon == null){
			Card c = deck.dealCard();
			if (murderRoom == null && c.checkType(Card.Type.ROOM)){
				murderRoom = c;
			}
			else if (murderCharacter == null && c.checkType(Card.Type.CHARACTER)){
				murderCharacter = c;
			}
			else if (murderWeapon == null && c.checkType(Card.Type.WEAPON)){
				murderWeapon = c;
			}
			else {
				deck.placeCard(c);
			}
		}
	}
	
	
	public void checkGuess(Guess guess){
		
	}
	
	
}
