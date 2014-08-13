package cludo.util;

import cludo.gui.CludoBoard;

public class Move {
	
	private static CludoBoard board;
	
	public Move(CludoBoard board){
		this.board = board;
	}
	
	public static boolean moveToRoom(Location oldLocation, Location newLocation){
		
		return true;
	}
	
	public static boolean canMoveHere(Location to, Location from){
		return board.isAdjacent(to, from);
	}
	
	public static boolean canMoveHere(Location location){
		return board.isSquareEmpty(location);
	}
	
}
