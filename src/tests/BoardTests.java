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
	public void doorTest1() {
		CludoBoard board = makeBoard(filePath+"doorTest.txt");
		assertTrue(board.isDoor(new Location(0, 0)));
	}
	
	@Test
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
	public void roomTest2(){
		
	}
	
	
	private CludoBoard makeBoard(String fileName){
		return new CludoBoard(new File(fileName));
	}
	
	private List<Player> makePlayerList(List<String> playerNames, List<String> characterNames, CludoBoard board){
		List<Player> playerList = new ArrayList<Player>();
		for (int i = 0; i < playerNames.size() && i <characterNames.size(); i++){
			playerList.add(new Player(playerNames.get(i), new Character(characterNames.get(i)), board.findSpawn(characterNames.get(i)), new Dice()));
		}
		return playerList;
	}
	
	private List<String> getPlayerNameList(){
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[]{"Jim" , "Tim", "Lim", "Sim", "Fim"};
		for (String n: names){
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}
	
	private List<String> getCharacterNameList(){
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[]{"Miss Scarlet" , "Professor Plum", "Reverand Green", "Mrs White", "Colonel Mustard"};
		for (String n: names){
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}
	
	private List<String> getBadCharacterNameList(){
		List<String> arrayListOfNames = new ArrayList<String>();
		String[] names = new String[]{"jim" , "tim", "cat", "legs", "happyCamper"};
		for (String n: names){
			arrayListOfNames.add(n);
		}
		return arrayListOfNames;
	}

}
