package cludo.game;

import java.util.*;

import cludo.game.cards.Card;
import cludo.game.cards.Card.Type;
import cludo.game.player.Player;
import cludo.gui.CludoBoard;
import cludo.util.Location;

public class Room {	
	
	private List<Player> occupants;
	private List<Weapon> weapons;
	private String name;
	private List<Location> doors;
	
	/**
	 * Constructs a room using a name and the list of doors locations attached to it.
	 * @param name - name of the room must be spelt correctly
	 * @param doors - lsit of doors that a player may enter and leave the room through.
	 */
	public Room (String name, List<Location> doors){
		occupants = new ArrayList<Player>();
		weapons = new ArrayList<Weapon>();
		this.doors = doors;
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Room [occupants=" + occupants + ", weapons=" + weapons
				+ ", name=" + name + ", doors=" + doors + "]";
	}

	/**
	 * Returns the amount of players in a room.
	 * @return
	 */
	public int getAmountOfPlayers(){
		return occupants.size();
	}
	
	/**
	 * gets the ammount of weapons in a room.
	 * @return - return the amount of wepons in the room.
	 */
	public int getAmountOfWeapons(){
		return weapons.size();
	}
	
	/**
	 * Checks if a door is part of the room.
	 * @param location - the location of the door.
	 * @return - return true if it is part of thius room false otherwise.
	 */
	public boolean checkDoor(Location location){
		return doors.contains(location);
	}

	/**
	 * Used to check if the player is in the room.
	 * @param player - the player that is to be checked.
	 * @return - returns true if the player is in the room false otherwise.
	 */
	public boolean hasPlayer(Player player) {
		return occupants.contains(player);
	}
	
	/**
	 * Has the player leave the room.
	 * @param player - player that is to leave the room.
	 */
	public void playerLeavesRoom(Player player) {
		occupants.remove(player);
	}

	/**
	 * Has the player enter the room 
	 * @param player - player that is to enter the room.
	 */
	public void playerEntersRoom(Player player) {
		occupants.add(player);
	}
	
	/**
	 * Gets the char type for this room.
	 * @return - gets the char that is this room.
	 */
	public char getRoomType(){
		if (name.equals("Kitchen")) return CludoBoard.kitchen;
		if (name.equals("Hall")) return CludoBoard.hall;
		if (name.equals("Study")) return CludoBoard.study;
		if (name.equals("BallRoom")) return CludoBoard.ballroom;
		if (name.equals("DiningRoom")) return CludoBoard.diningRoom;
		if (name.equals("Conservatory")) return CludoBoard.conservatory;
		if (name.equals("Library")) return CludoBoard.library;
		if (name.equals("BilliardRoom")) return CludoBoard.billiardRoom;
		return CludoBoard.lounge;
	}

	/**
	 * Gets the Card that represents this room.
	 * @return - returns the card that is of this room 
	 */
	public Card getCard() {
		if (name.equals("Kitchen")) return new Card(Card.Type.ROOM, "Kitchen");
		if (name.equals("Hall")) return new Card(Card.Type.ROOM, "Hall");
		if (name.equals("Study")) return new Card(Card.Type.ROOM, "Study");
		if (name.equals("BallRoom")) return new Card(Card.Type.ROOM, "Ballroom");
		if (name.equals("DiningRoom")) return new Card(Card.Type.ROOM, "DiningRoom");
		if (name.equals("Conservatory")) return new Card(Card.Type.ROOM, "Conservatory");
		if (name.equals("Library")) return new Card(Card.Type.ROOM, "Library");
		if (name.equals("BilliardRoom")) return new Card(Card.Type.ROOM, "BilliardRoom");
		return new Card(Card.Type.ROOM, "Lounge");
	}
}
