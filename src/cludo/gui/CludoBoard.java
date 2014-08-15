package cludo.gui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import org.hamcrest.core.IsCollectionContaining;

import cludo.Main;
import cludo.game.Room;
import cludo.game.cards.Card;
import cludo.game.cards.Deck;
import cludo.game.guess.Solution;
import cludo.game.guess.Suggestion;
import cludo.game.player.Player;
import cludo.util.Location;

public class CludoBoard {

	private char[][] board;
	private List<Player> playerList;
	private Deck deck = new Deck();
	private Solution solution;
	private List<Room> rooms;
	private boolean hasStarted;

	// Turn order
	private Queue<Player> turn;

	// ===========BoardTable==============//
	// Rooms
	public static final char kitchen = 'K';
	public static final char ballroom = 'B';
	public static final char conservatory = 'C';
	public static final char library = 'L';
	public static final char study = 'S';
	public static final char hall = 'H';
	public static final char diningRoom = 'D';
	public static final char billiardRoom = 'I';
	public static final char lounge = 'O';
	private final char caller = 'R';

	// Secret passages
	private final char secretPassageOne = '&';
	private final char secretPassageTwo = '|';

	// Doors and the direction
	private final char northDoor = 'N';
	private final char southDoor = 's';
	private final char eastDoor = 'e';
	private final char westDoor = 'w';

	// Spawns
	private final char spawnWhite = 'W';
	private final char spawnGreen = 'G';
	private final char spawnPlum = 'P';
	private final char spawnScarlet = 'A';
	private final char spawnMustard = 'M';
	private final char spawnPeacock = 'E';

	// EmptySquare
	private final char emptySquare = 'n';

	// =====================================//

	/**
	 * Constructs the game board calling a methods to load in the file previded.
	 * 
	 * @param gameBoard
	 *            -File that contains the game board.
	 */
	public CludoBoard(File gameBoard) {
		board = loadBoard(gameBoard);
		rooms = new ArrayList<Room>();
		test();
	}

	public Location findSpawn(String character) {
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if (nameToChar(character) == board[col][row]) {
					return new Location(row, col);
				}
			}
		}
		return null;
	}

	public boolean startGame(List<Player> playerList) {
		setPlayers(playerList);
		setTurnOrder();
		setSolution();
		dealCards();
		setRooms();
		hasStarted = true;
		return true;
	}

	private void setRooms() {
		rooms.add(new Room("Study", findDoors(study)));
		rooms.add(new Room("Kitchen", findDoors(kitchen)));
		rooms.add(new Room("BallRoom", findDoors(ballroom)));
		rooms.add(new Room("DiningRoom", findDoors(diningRoom)));
		rooms.add(new Room("Conservatory", findDoors(conservatory)));
		rooms.add(new Room("Library", findDoors(library)));
		rooms.add(new Room("Hall", findDoors(hall)));
		rooms.add(new Room("Lounge", findDoors(lounge)));
		for (Room r: rooms){
			System.out.println(r);
		}
	}

	private List<Location> findDoors(char room) {
		List<Location> listOfLocations = new ArrayList<Location>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[j][i] == northDoor || board[j][i] == southDoor
						|| board[j][i] == eastDoor || board[j][i] == westDoor) {
					if (board[j+1][i] == room) {
						listOfLocations.add(new Location(i, j));
					} else if (board[j-1][i] == room) {
						listOfLocations.add(new Location(i, j));
					} else if (board[j][i+1] == room) {
						listOfLocations.add(new Location(i, j));
					} else if (board[j][i-1] == room) {
						listOfLocations.add(new Location(i, j));
					}
				}
			}
		}
		return listOfLocations;
	}

	private void dealCards() {
		while (!deck.isEmpty()) {
			Player p = turn.poll();
			p.reciveCard(deck.dealCard());
			turn.offer(p);
		}
		while (!turn.peek().isTurn()) {
			Player p = turn.poll();
			turn.offer(p);
		}
	}

	private void setSolution() {
		solution = new Solution(deck);
	}

	public char[][] loadBoard(File gameBoard) {
		char[][] temp = new char[25][25];
		try {
			BufferedReader load = new BufferedReader(new FileReader(gameBoard));
			for (int i = 0; i < temp.length; i++) {
				temp[i] = load.readLine().toCharArray();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp;
	}

	public void test() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	protected char[][] getBoard() {
		return board;
	}

	private char nameToChar(String name) {
		if (name.equals("Miss Scarlet")) {
			return spawnScarlet;
		} else if (name.equals("Mrs White")) {
			return spawnWhite;
		} else if (name.equals("Mrs Peacock")) {
			return spawnPeacock;
		} else if (name.equals("Colonel Mustard")) {
			return spawnMustard;
		} else if (name.equals("Professor Plum")) {
			return spawnPlum;
		} else {
			return spawnGreen;
		}
	}

	private void setPlayers(List<Player> playerList) {
		this.playerList = playerList;
	}

	private boolean setTurnOrder() {
		if (playerList == null) {
			return false;
		}
		turn = new LinkedList<Player>();
		Collections.shuffle(playerList);
		for (Player p : playerList) {
			turn.add(p);
		}
		turn.peek().setTurn();
		return true;
	}

	public int getWidth() {
		return board.length;
	}

	public int getHeight() {
		return board[0].length;
	}

	public boolean isAdjacent(Location to, Location from) {
		if (to.x >= 0 && to.x < board.length && to.y >= 0
				&& to.y < board.length) {
			char squareTo = board[to.y][to.x];
			char squareFrom = board[from.y][from.x];
			if (squareTo == emptySquare && !isDoor(from)) {
				return true;
			}
			if (squareTo == 'N' && from.y == to.y - 1) {
				return true;
			}
			if (squareTo == 's' && from.y == to.y + 1) {
				return true;
			}
			if (squareTo == 'e' && from.x == to.x - 1) {
				return true;
			}
			if (squareTo == 'w' && from.x == to.x + 1) {
				return true;
			}
			if (squareFrom == 'N' && from.y == to.y + 1) {
				return true;
			}
			if (squareFrom == 's' && from.y == to.y - 1) {
				return true;
			}
			if (squareFrom == 'e' && from.x == to.x + 1) {
				return true;
			}
			if (squareFrom == 'w' && from.x == to.x - 1) {
				return true;
			}
		}
		return false;
	}
	
	public void handleSuggestion(Suggestion suggestion){
		Player turnPlayer = turn.poll();
		turn.offer(turnPlayer);
		Card c = null;
		Player p = null;
		Player refuteingPlayer = null;
		while (!turn.peek().isTurn()){
			//Main.canvas.repaint();
			p = turn.poll();
			c = p.refute(suggestion, turnPlayer);
			turn.offer(p);
			if (c != null){
				refuteingPlayer = p;
				break;
			}
		}
		while(!turn.peek().isTurn()){
			p = turn.poll();
			p.showSuggestion(suggestion, turnPlayer);
			turn.offer(p);
		}
		turn.peek().showRefute(c, refuteingPlayer);
	}

	public boolean isSquareEmpty(Location location) {
		if (location.x >= 0 && location.x < board.length && location.y >= 0
				&& location.y < board.length) {
			char squareTo = board[location.y][location.x];
			if (squareTo == emptySquare) {
				return true;
			}
			if (squareTo == spawnGreen || squareTo == spawnMustard
					|| squareTo == spawnPeacock || squareTo == spawnPlum
					|| squareTo == spawnScarlet || squareTo == spawnWhite) {
				return true;
			}
			if (isDoor(location)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isInRoom(Player player){
		for (Room r: rooms){
			if (r.hasPlayer(player)){
				return true;
			}
		}
		return false;
	}
	
	public Room getRoomPlayerIsIn(Player player){
		for (Room r: rooms){
			if (r.hasPlayer(player)){
				return r;
			}
		}
		return null;
	}
	
	public boolean moveToAndFromRooms(Location location){
		if (isDoor(location)){
			for (Room r: rooms){
				if(r.checkDoor(location)){
					if (r.hasPlayer(getTurnPlayer())){
						r.playerLeavesRoom(getTurnPlayer());
						return true;
					}
					else {
						r.playerEntersRoom(getTurnPlayer());
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isRoom(Location location) {
		char place = getCharAtLocation(location);
		if (place == 'K' || place == 'B' || place == 'C' || place == 'L'
				|| place == 'S' || place == 'H' || place == 'D' || place == 'I'
				|| place == 'O' || place == 'R') {
			return true;
		}
		return false;
	}
	
	/**
	 * requires  location is not null
	 * ensure that the it will return true if the location is of the room type else false
	 * @param location - places on the board
	 * @param roomType - room that the place might be in
	 * @return - boolean relating to the location.
	 */
	public boolean isRoom(Location location, char roomType){
		char place = getCharAtLocation(location);
		if (roomType == place) return true;
		return false;
	}

	public boolean isOutterWall(Location location) {
		char place = getCharAtLocation(location);
		if (place == 'Q') {
			return true;
		}
		return false;
	}

	public boolean isFloor(Location location) {
		char place = getCharAtLocation(location);
		if (place == 'n') {
			return true;
		}
		return false;
	}

	public boolean isSpawn(Location location) {
		char place = getCharAtLocation(location);
		if (place == spawnWhite || place == spawnPeacock
				|| place == spawnMustard || place == spawnScarlet
				|| place == spawnPlum || place == spawnGreen) {
			return true;
		}
		return false;
	}

	public boolean isDoor(Location location) {
		char place = getCharAtLocation(location);
		if (place == northDoor || place == southDoor || place == eastDoor
				|| place == westDoor) {
			return true;
		}
		return false;
	}
	
	private char getCharAtLocation(Location location){
		return board[location.y][location.x];
	}

	public Player getTurnPlayer() {
		return turn.peek();
	}
	
	public void endTurn(){
		Player player = turn.poll();
		player.setTurn();
		turn.offer(player);
		turn.peek().setTurn();
	}
	
	public boolean hasStarted(){
		return hasStarted;
	}

}
