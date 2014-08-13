package cludo.util;

import java.util.*;

import cludo.game.Room;
import cludo.game.player.Player;
import cludo.gui.CludoBoard;

public class Move {
	
	private static CludoBoard board;
	
	public Move(CludoBoard board){
		this.board = board;
	}
	
	/**
	 * takes the old location of the player the new one and the player and will tell 
	 * player who called it id they can move or not and handle movement in and out of rooms.
	 * @param oldLocation - the location that the player is.
	 * @param newLocation - the location the player wishes to move.
	 * @param player - the player that is the player calling the method.
	 * @return - returns true if the player can move to this point, false otherwise.
	 */
	public static boolean makeMove(Location oldLocation, Location newLocation, Player player){
		int playerMoves = player.getCurrentMove();
		List<Location> playersAttemptedMove = player.currentPath;
		if(board.isInRoom(player)){
			Room playersRoom = board.getRoomPlayerIsIn(player);
			if (board.isRoom(newLocation, playersRoom.getRoomType())){
				return true;
			}
			else if(playersRoom.checkDoor(newLocation)){
				board.moveToAndFromRooms(newLocation);
				return true;
			}
		}
		else{
			if (playersAttemptedMove != null && playersAttemptedMove.size()-1 <= playerMoves){
				if (board.isDoor(newLocation)){
					board.moveToAndFromRooms(newLocation);
					player.updateMove(0);
					return true;
				}
				else if (board.isFloor(newLocation)){
					player.updateMove(player.getCurrentMove()-(playersAttemptedMove.size()-1));
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean canMoveHere(Location to, Location from){
		return board.isAdjacent(to, from);
	}
	
	public static boolean canMoveHere(Location location){
		return board.isSquareEmpty(location);
	}
	
}
