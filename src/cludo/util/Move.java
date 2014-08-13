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
