package cludo.game.guess;

import cludo.game.cards.Card;

public class Accuse {
	private Card murderRoom;
	private Card murderCharacter;
	private Card murderWeapon;
	
	public Accuse(Card murderRoom, Card murderCharacter, Card murderWeapon) {
		super();
		this.murderRoom = murderRoom;
		this.murderCharacter = murderCharacter;
		this.murderWeapon = murderWeapon;
	}

	public Card getMurderRoom() {
		return murderRoom;
	}

	public Card getMurderCharacter() {
		return murderCharacter;
	}

	public Card getMurderWeapon() {
		return murderWeapon;
	}
	
	
}
