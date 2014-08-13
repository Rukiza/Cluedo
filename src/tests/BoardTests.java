package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import cludo.gui.CludoBoard;
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
					fail("The board is filled with doors");
				}
			}
		}
	}
	
	
	private CludoBoard makeBoard(String fileName){
		return new CludoBoard(new File(fileName));
	}

}
