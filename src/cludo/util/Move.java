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
		if (newLocation.y > board.getHeight() || newLocation.x > board.getWidth()){
			return false;
		}
		int playerMoves = player.getCurrentMove();
		List<Location> playersAttemptedMove = player.currentPath;
		// logic for if the player is in a room 
		if(board.isInRoom(player)){
			Room playersRoom = board.getRoomPlayerIsIn(player);
			if (board.isRoom(newLocation, playersRoom.getRoomType())){
				// the player is in a room and he/ she is trying to move around the room that they are in.
				return true;
			}
			else if(playersRoom.checkDoor(newLocation)){
				// if they move to the door of there room moves them out of the room.
				board.moveToAndFromRooms(newLocation);
				return true;
			}else if(board.isSecretPassage(newLocation) && (player.getCurrentMove() != 0 || !player.hasRolled())){
				// makes sure that the player is able to move in a secret passageway.
				// and will move them from the room there in and moves them to a the other secret passage.
				board.moveToAndFromRooms(newLocation);
				Location otherSecret = board.findOtherSecret(newLocation);
				player.setLocation(otherSecret);
				board.moveToAndFromRooms(otherSecret);
				player.updateMove(0);
				// error in handling of move;
				return false;
			}
		}
		else{ // logic for if the player is not in a room 
			if (playersAttemptedMove != null && playersAttemptedMove.size()-1 <= playerMoves){
				// if the player can move he can do to action move to a floor tile 
				// or enter a room.
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
	
	/**
	 * Uses a board to check if a player can move to the position they would like.
	 * @param to - position they came from.
	 * @param from - position they are moving to.
	 * @return returns true if they can move there.
	 */
	public static boolean canMoveHere(Location to, Location from){
		return board.isAdjacent(to, from);
	}
	
	/**
	 * Checks if the location can atuly be moved to.
	 * @param location - location that is to be checked
	 * @return - returns true if it can be moved to.
	 */
	public static boolean canMoveHere(Location location){
		return board.isSquareEmpty(location);
	}
	
}
