package tests;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

import cludo.gui.CludoBoard;

public class BoardTests {

	@Test
	public void doorTest1() {
		fail("Not yet implemented");
	}
	
	
	private CludoBoard makeBoard(File file){
		return new CludoBoard(file);
	}

}
