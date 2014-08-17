package cludo.util;

public class Dice {
	
	private int rollOne;
	private int rollTwo;
	
	/**
	 * returns the roll from dice one.
	 * @return
	 */
	public int getRollOne(){
		return rollOne;
	}
	
	/**
	 * returns the roll from dice two
	 * @return
	 */
	public int getRollTwo(){
		return rollTwo;
	}
	
	/**
	 * rolls both dice and returns the roll of both of them
	 * @return
	 */
	public int roll(){
		rollOne = (int)(Math.random()*6) +1;
		rollTwo = (int)(Math.random()*6) +1;
		return rollOne + rollTwo;
	}
}
