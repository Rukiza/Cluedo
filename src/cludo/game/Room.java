package cludo.game;

import java.util.*;

import cludo.game.player.Player;
import cludo.gui.CludoBoard;
import cludo.util.Location;

public class Room {	
	
	private List<Player> occupants;
	private List<Weapon> weapons;
	private String name;
	private List<Location> doors;
	
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

	public int getAmountOfPlayers(){
		return occupants.size();
	}
	
	public int getAmountOfWeapons(){
		return weapons.size();
	}
	
	public boolean checkDoor(Location location){
		return doors.contains(location);
	}

	public boolean hasPlayer(Player player) {
		return occupants.contains(player);
	}

	public void playerLeavesRoom(Player player) {
		occupants.remove(player);
	}

	public void playerEntersRoom(Player player) {
		occupants.add(player);
	}
	
	public char getRoomType(){
		if (name.equals("Kitchen")) return CludoBoard.kitchen;
		if (name.equals("Hall")) return CludoBoard.hall;
		if (name.equals("Study")) return CludoBoard.study;
		if (name.equals("BallRoom")) return CludoBoard.ballroom;
		if (name.equals("DiningRoom")) return CludoBoard.diningRoom;
		if (name.equals("Conservatory")) return CludoBoard.conservatory;
		if (name.equals("Library")) return CludoBoard.library;
		return CludoBoard.lounge;
	}
}
