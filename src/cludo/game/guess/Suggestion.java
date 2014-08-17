package cludo.game.guess;

import java.util.*;

import cludo.game.cards.Card;
import cludo.game.cards.Hand;

public class Suggestion implements Iterable<Card>{
	
	private Card murderRoom;
	private Card murderWeapon;
	private Card murderCharacter;
	
	/**
	 * Constructs a sugestion depending on what the player picked
	 * @param murderRoom - room that was suggested
	 * @param murderWeapon - weapon that was suggested
	 * @param murderCharacter -  character that was suggested.
	 */
	public Suggestion (Card murderRoom, Card murderWeapon, Card murderCharacter){
		this.murderRoom = murderRoom;
		this.murderWeapon = murderWeapon;
		this.murderCharacter = murderCharacter;
	}
	
	/**
	 * Logic for the suggestion being refuted based on the players hand.
	 * @param playerHand -  playerhand that is doing the refuting.
	 * @return - returns a list of cards that the player can refute with.
	 */
	public List<Card> refute(Hand playerHand){
		List<Card> cardsThatMatch = new ArrayList<Card>();
		for (Card c: playerHand){
			if (c.equals(murderRoom)){
				cardsThatMatch.add(murderRoom);
			}
			else if (c.equals(murderWeapon)){
				cardsThatMatch.add(murderWeapon);
			}
			else if (c.equals(murderCharacter)){
				cardsThatMatch.add(murderCharacter);
			}
		}
		return cardsThatMatch;
	}

	@Override
	public Iterator<Card> iterator() {
		List<Card> temp = new ArrayList<Card>();
		temp.add(murderRoom);
		temp.add(murderCharacter);
		temp.add(murderWeapon);
		return temp.iterator();
	}
}
