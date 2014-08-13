package cludo.game.guess;

import java.util.*;

import cludo.game.cards.Card;
import cludo.game.cards.Hand;

public class Suggestion {
	
	private Card murderRoom;
	private Card murderWeapon;
	private Card murderCharacter;
	
	public Suggestion (Card murderRoom, Card murderWeapon, Card murderCharacter){
		this.murderRoom = murderRoom;
		this.murderWeapon = murderWeapon;
		this.murderCharacter = murderCharacter;
	}
	
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
}
