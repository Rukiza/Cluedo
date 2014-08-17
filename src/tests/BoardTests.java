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

public class BoardTests {
	
	private String filePath = "TestBoards/";

	@Test
	/**
	 * The first square is a door so it should not fail.
	 * Checks the first quare is a door.
	 */
	public void doorTest1() {
		CludoBoard board = makeBoard(filePath+"doorTest.txt");
		assertTrue(board.isDoor(new Location(0, 0)));
	}
	
	@Test
	/**
	 * Tests to see that there are doors on every square of the map when there is
	 */
	public void doorTest2()	{
		CludoBoard board = makeBoard(filePath+"doorTest.txt");
		for (int x = 0; x < board.getWidth(); x++){
			for (int y = 0; y < board.getHeight(); y++){
				if (!board.isDoor(new Location(0, 0))){
					fail("The board is filled with doors");
				}
			}
		}
	}
	
	@Test
	/**
	 * Tests that the board has rooms on it when it does
	 */
	public void roomTest1(){
		CludoBoard board = makeBoard(filePath+ "roomTest.txt");
		for (int x = 0; x < board.getWidth(); x++){
			for (int y = 0; y < board.getHeight(); y++){
				if (board.isDoor(new Location(0, 0))){
				}else if (board.isRoom(new Location(x, y))){
				}else {
					fail("The board is filled with doors and rooms");
				}
			}
		}
	}
	
	
	@Test
	/**
	 * Test to check that no player should start out in a room
	 */
	public void roomTest2(){
		CludoBoard board = makeBoard("CludoGameBoard");
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), board);
		board.startGame(playerList);
		for (Player player: playerList){
			if(board.isInRoom(player)){
				fail("No player should start out in a room.");
			}
		}
	}
	
	@Test
	/**
	 * Test for when player enter a room it says they are in thr room .
	 */
	public void roomTest3(){
		CludoBoard board = makeBoard("CludoGameBoard.txt");
		List<Player> playerList = makePlayerList(getPlayerNameList(), getCharacterNameList(), board);
		board.startGame(playerList);
		int i = 0;
		for (int x = 0; x < board.getWidth(); x++){
			for (int y = 0; y<board.getHeight(); y++){
				Location l = new Location(x, y);
				if (board.isDoor(l)){
					board.getTurnPlayer().setLocation(l);
					board.moveToAndFromRooms(l);
					board.endTurn();
					i++;
				}
				if(i >= playerList.size()) break;
			}
			if (i >= playerList.size())break;
		}
		for (Player player: playerList){
			if(!board.isInRoom(player)){
				fail("All Players hsould be in rooms");
			}
		}
	}
	

	
	/**
	 * Helper method that makes a board from a file name.
	 * @param fileName - name of the file.
	 * @return - constructed board.
	 */
	private CludoBoard makeBoard(String fileName){
		return new CludoBoard(new File(fileName));
	}
	
	/**
	 * Helper method for making a list of players
	 * @param playerNames - names of players
	 * @param characterNames - names of characters
	 * @param board - the board they will be added to.
	 * @return - a player list.
	 */
	private List<Player> makePlayerList(List<String> playerNames, List<String> characterNames, CludoBoard board){
		List<Player> playerList = new ArrayList<Player>();
		for (int i = 0; i < playerNames.size() && i <characterNames.size(); i++){
			playerList.add(new Player(playerNames.get(i), new Character(characterNames.get(i)), board.findSpawn(characterNames.get(i)), new Dice()));
		}
		return playerList;
	}
	
	/**
	 * Makes a list of player names 
	 * @return - returns a list of player names.
	 */
	private List<String> getPlayerNameList(){
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[]{"Jim" , "Tim", "Lim", "Sim", "Fim"};
		for (String n: names){
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}
	
	/**
	 * Makes a list of character names
	 * @return - list of character names.
	 */
	private List<String> getCharacterNameList(){
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[]{"Miss Scarlet" , "Professor Plum", "Reverand Green", "Mrs White", "Colonel Mustard"};
		for (String n: names){
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}
	
	/**
	 * Makes a bad list of character names.
	 * @return
	 */
	private List<String> getBadCharacterNameList(){
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[]{"jim" , "tim", "cat", "legs", "happyCamper"};
		for (String n: names){
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}

}
