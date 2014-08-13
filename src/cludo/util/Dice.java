package cludo.util;

public class Dice {
	
	private int rollOne;
	private int rollTwo;
	
	public int getRollOne(){
		return rollOne;
	}
	
	public int getRollTwo(){
		return rollTwo;
	}
	
	public int roll(){
		rollOne = (int)(Math.random()*6) +1;
		rollTwo = (int)(Math.random()*6) +1;
		return rollOne + rollTwo;
	}
}
