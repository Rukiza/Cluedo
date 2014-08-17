package tests;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import cludo.game.player.*;
import cludo.game.player.Character;
import cludo.gui.CludoBoard;
import cludo.util.Dice;
import cludo.util.Location;
import cludo.util.Move;
import cludo.util.PathFinder;

public class BoardTests {

	private String filePath = "TestBoards/";

	@Test
	/**
	 * The first square is a door so it should not fail.
	 * Checks the first quare is a door.
	 */
	public void doorTest1() {
		CludoBoard board = makeBoard(filePath + "doorTest.txt");
		assertTrue(board.isDoor(new Location(0, 0)));
	}

	@Test
	/**
	 * Tests to see that there are doors on every square of the map when there is
	 */
	public void doorTest2() {
		CludoBoard board = makeBoard(filePath + "doorTest.txt");
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				if (!board.isDoor(new Location(0, 0))) {
					fail("The board is filled with doors");
				}
			}
		}
	}

	@Test
	/**
	 * Tests that the board has rooms on it when it does
	 */
	public void roomTest1() {
		CludoBoard board = makeBoard(filePath + "roomTest.txt");
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				if (board.isDoor(new Location(0, 0))) {
				} else if (board.isRoom(new Location(x, y))) {
				} else {
					fail("The board is filled with doors and rooms");
				}
			}
		}
	}

	@Test
	/**
	 * Test to check that no player should start out in a room
	 */
	public void roomTest2() {
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(),
				getCharacterNameList(), board);
		board.startGame(playerList);
		for (Player player : playerList) {
			if (board.isInRoom(player)) {
				fail("No player should start out in a room.");
			}
		}
	}

	@Test
	/**
	 * Test for when player enter a room it says they are in the room .
	 */
	public void roomTest3() {
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(),
				getCharacterNameList(), board);
		board.startGame(playerList);
		int i = 0;
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Location l = new Location(x, y);
				if (board.isDoor(l)) {
					board.getTurnPlayer().setLocation(l);
					board.moveToAndFromRooms(l);
					board.endTurn();
					i++;
				}
				if (i >= playerList.size())
					break;
			}
			if (i >= playerList.size())
				break;
		}
		for (Player player : playerList) {
			if (!board.isInRoom(player)) {
				fail("All Players hsould be in rooms");
			}
		}
	}

	@Test
	/**
	 * Tests moving into a room form a secret passage.
	 */
	public void roomTest4() {
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(),
				getCharacterNameList(), board);
		board.startGame(playerList);
		int i = 0;
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Location l = new Location(x, y);
				if (board.isSecretPassage(l)) {
					board.getTurnPlayer().setLocation(l);
					board.moveToAndFromRooms(l);
					board.endTurn();
					i++;
				}
				if (i >= playerList.size())
					break;
			}
			if (i >= playerList.size())
				break;
		}

		for (i = 0; i < 4; i++) {
			if (!board.isInRoom(playerList.get(i))) {
				fail("All Players hsould be in rooms");
			}
		}
	}
	
	@Test
	/**
	 * a floor is a floor and is always empty.
	 */
	public void floorTest1(){
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(),
				getCharacterNameList(), board);
		board.startGame(playerList);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Location l = new Location(x, y);
				if (board.isFloor(l)) {
					board.getTurnPlayer().setLocation(l);
					board.endTurn();
				}
			}
		}
		for (Player player: playerList){
			if(!board.isFloor(player.getLocation())){
				fail("Players location should be a floor");
			}
			if (!board.isSquareEmpty(player.getLocation())){
				fail("Player square should be empty");
			}
		}
	}
	
	@Test
	/**
	 * Checks that the name enters was the name that was saved
	 */
	public void playerTest1(){
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), makeBoard("CludoGameBoard.txt"));
		assertEquals("Players name should equal the name they are given", playerList.get(0).getName(), "Jim");
	}
	
	@Test
	/**
	 * Checks that the characters name was correct
	 */
	public void playerTest2(){
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), makeBoard("CludoGameBoard.txt"));
		assertEquals("Players characters name should equal", playerList.get(0).getCharacterName(), "Miss Scarlet");
	}
	
	@Test
	/**
	 * Checks that is starts out at the spawn location
	 */
	public void playerTest3(){
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), board);
		for (int x = 0; x < board.getWidth(); x++){
			for (int y = 0; y < board.getHeight(); y++){
				Location l = new Location(x, y);
				Player p = playerList.get(0);
				if (board.findSpawn(p.getCharacterName()).equals(l) && !l.equals(p.getLocation())){
					fail("Players starting location should be there spawn location");
				}
			}
		}
	}
	

	
	@Test
	/**
	 * Tests the pathfinder should return a list larger than two
	 */
	public void pathFinderTest1(){
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), board);
		board.startGame(playerList);
		Move move = new Move(board);
		// path finder requires move.
		PathFinder pathfinder = new PathFinder();
		for (int x = 0; x < board.getWidth(); x++){
			for (int y = 0; y < board.getHeight(); y++){
				Location l = new Location(x, y);
				if (board.isFloor(l) || board.isDoor(l)){
					List<Location> location = pathfinder.findPath(playerList.get(0).getLocation(), l);
					if (!l.equals(playerList.get(0).getLocation()) && location.size() < 2){
						fail("Path should always contain two location if it is one space away and is mmovable to");
					}
				}
			}
		}
	}
	
	@Test
	/**
	 * checks that paths that involve rooms or outter walls cant be made.
	 */
	public void pathFinderTest2(){
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), board);
		board.startGame(playerList);
		Move move = new Move(board);
		// path finder requires move.
		PathFinder pathfinder = new PathFinder();
		for (int x = 0; x < board.getWidth(); x++){
			for (int y = 0; y < board.getHeight(); y++){
				Location l = new Location(x, y);
				if (board.isRoom(l) || board.isOutterWall(l)){
					List<Location> location = pathfinder.findPath(playerList.get(0).getLocation(), l);
					if(location != null){
						fail("if the place is unreacheable it should return null");
					}
				}
			}
		}
	}
	


	/**
	 * Helper method that makes a board from a file name.
	 * 
	 * @param fileName
	 *            - name of the file.
	 * @return - constructed board.
	 */
	private CludoBoard makeBoard(String fileName) {
		return new CludoBoard(new File(fileName));
	}

	/**
	 * Helper method for making a list of players
	 * 
	 * @param playerNames
	 *            - names of players
	 * @param characterNames
	 *            - names of characters
	 * @param board
	 *            - the board they will be added to.
	 * @return - a player list.
	 */
	private List<Player> makePlayerList(List<String> playerNames,
			List<String> characterNames, CludoBoard board) {
		List<Player> playerList = new ArrayList<Player>();
		for (int i = 0; i < playerNames.size() && i < characterNames.size(); i++) {
			playerList.add(new Player(playerNames.get(i), new Character(
					characterNames.get(i)), board.findSpawn(characterNames
					.get(i)), new Dice()));
		}
		return playerList;
	}

	/**
	 * Makes a list of player names
	 * 
	 * @return - returns a list of player names.
	 */
	private List<String> getPlayerNameList() {
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[] { "Jim", "Tim", "Lim", "Sim", "Fim" };
		for (String n : names) {
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}

	/**
	 * Makes a list of character names
	 * 
	 * @return - list of character names.
	 */
	private List<String> getCharacterNameList() {
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[] { "Miss Scarlet", "Professor Plum",
				"Reverand Green", "Mrs White", "Colonel Mustard" };
		for (String n : names) {
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}

	/**
	 * Makes a bad list of character names.
	 * 
	 * @return
	 */
	private List<String> getBadCharacterNameList() {
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[] { "jim", "tim", "cat", "legs",
				"happyCamper" };
		for (String n : names) {
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}

}
