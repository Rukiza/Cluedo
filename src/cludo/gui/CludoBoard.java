package cludo.gui;

import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

import cludo.Main;
import cludo.game.Room;
import cludo.game.cards.Card;
import cludo.game.cards.Deck;
import cludo.game.guess.Accuse;
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
	private boolean gameOver;

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

	/**
	 * Starts the game off
	 * 
	 * @param playerList
	 *            - requires a list of players that are to be in the game
	 * @return - returns a boolean for if the game has started always true;
	 */
	public boolean startGame(List<Player> playerList) {
		setPlayers(playerList);
		setTurnOrder();
		setSolution();
		dealCards();
		setRooms();
		hasStarted = true;
		return true;
	}

	/**
	 * Sets up the rooms
	 */
	private void setRooms() {
		rooms.add(new Room("Study", findDoors(study)));
		rooms.add(new Room("Kitchen", findDoors(kitchen)));
		rooms.add(new Room("BallRoom", findDoors(ballroom)));
		rooms.add(new Room("DiningRoom", findDoors(diningRoom)));
		rooms.add(new Room("Conservatory", findDoors(conservatory)));
		rooms.add(new Room("Library", findDoors(library)));
		rooms.add(new Room("Hall", findDoors(hall)));
		rooms.add(new Room("Lounge", findDoors(lounge)));
		rooms.add(new Room("BilliardRoom", findDoors(billiardRoom)));
	}

	/**
	 * Finds the doors of rooms
	 * 
	 * @param room
	 *            - requires a chard for room we are looking for doors two
	 * @return - returns a list of doors to a room matching the char.
	 */
	private List<Location> findDoors(char room) {
		List<Location> listOfLocations = new ArrayList<Location>();
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[j][i] == northDoor || board[j][i] == southDoor
						|| board[j][i] == eastDoor || board[j][i] == westDoor) {
					if (board[j + 1][i] == room) {
						listOfLocations.add(new Location(i, j));
					} else if (board[j - 1][i] == room) {
						listOfLocations.add(new Location(i, j));
					} else if (board[j][i + 1] == room) {
						listOfLocations.add(new Location(i, j));
					} else if (board[j][i - 1] == room) {
						listOfLocations.add(new Location(i, j));
					}
				}
			}
		}
		return listOfLocations;
	}

	/**
	 * Deals out the cards to each player.
	 */
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

	/**
	 * Sets up the solution to the game.
	 */
	private void setSolution() {
		solution = new Solution(deck);
	}

	/**
	 * Loads in the board file for the game.
	 * 
	 * @param gameBoard
	 *            - file name.
	 * @return - returns a char array that represents the array
	 */
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

	/**
	 * Method that prints the board.
	 */
	public void test() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
	}

	/**
	 * gets the board depricated ... not used anymore.
	 * 
	 * @return - returns the char reprsentaion of the board.
	 */
	protected char[][] getBoard() {
		return board;
	}

	/**
	 * converts a name to the char reprsentaion of there spawn point.
	 * 
	 * @param name
	 * @return
	 */
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

	/**
	 * Sets the players list.
	 * 
	 * @param playerList
	 */
	private void setPlayers(List<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * Sets up the turn order
	 * 
	 * @return
	 */
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

	/**
	 * Gets the width of the board logic incorect but board width and hight
	 * should both be 25
	 * 
	 * @return
	 */
	public int getWidth() {
		return board.length;
	}

	/**
	 * returns the hight of the board.
	 * 
	 * @return
	 */
	public int getHeight() {
		return board[0].length;
	}

	/**
	 * Logic for checking that 2 squares are ajasent takeinto acount that you
	 * can only enter a door from one direction.
	 * 
	 * @param to
	 *            - the location that they are going to.
	 * @param from
	 *            - where they are coming from.
	 * @return - return true if they can get there.
	 */
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

	/**
	 * Logic for handling the players suggestions
	 * 
	 * @param suggestion
	 *            - the suggestion a player may have.
	 */
	public void handleSuggestion(Suggestion suggestion) {
		Main.canvas.refuteDrawCase = true;
		Player turnPlayer = turn.poll();
		turn.offer(turnPlayer);
		Card c = null;
		Player p = null;
		Player refuteingPlayer = null;
		while (!turn.peek().isTurn()) {

			p = turn.poll();
			p.setRefuting(true);
			Main.canvas.repaint();
			c = p.refute(suggestion, turnPlayer);
			turn.offer(p);
			p.setRefuting(false);
			if (c != null) {
				refuteingPlayer = p;
				break;
			}
		}
		while (!turn.peek().isTurn()) {
			p.setRefuting(true);
			Main.canvas.repaint();
			p = turn.poll();
			p.showSuggestion(suggestion, turnPlayer);
			turn.offer(p);
			p.setRefuting(false);
		}
		turn.peek().showRefute(c, refuteingPlayer);
		Main.canvas.refuteDrawCase = false;
		Main.canvas.repaint();
	}

	/**
	 * Logic to tell if a square is empty
	 * 
	 * @param location
	 *            - the location that is to be checked
	 * @return - return true if empty false otherwise.
	 */
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

	/**
	 * Checks if the player is in a room
	 * 
	 * @param player
	 *            - player to be checked
	 * @return - returns true if the player is in a room false otherwise
	 */
	public boolean isInRoom(Player player) {
		for (Room r : rooms) {
			if (r.hasPlayer(player)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the room the player is in (player must be in room or null is
	 * returned
	 * 
	 * @param player
	 *            - player to get the room that he is in.
	 * @return - room the player is in or null
	 */
	public Room getRoomPlayerIsIn(Player player) {
		for (Room r : rooms) {
			if (r.hasPlayer(player)) {
				return r;
			}
		}
		return null;
	}

	/**
	 * Moves player to and from rooms
	 * 
	 * @param location
	 *            - location that the player is standing to do this.
	 * @return - if the location is a door the player will move in and out of
	 *         the room depending.
	 */
	public boolean moveToAndFromRooms(Location location) {
		if (isDoor(location)) {
			for (Room r : rooms) {
				if (r.checkDoor(location)) {
					if (r.hasPlayer(getTurnPlayer())) {
						r.playerLeavesRoom(getTurnPlayer());
						return true;
					} else {
						r.playerEntersRoom(getTurnPlayer());
						return true;
					}
				}
			}
		}
		return false;
	}

	/**
	 * check that a location is a room or not.
	 * 
	 * @param location
	 *            - location to be checked
	 * @return- true if the location is a room false otherwise.
	 */
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
	 * requires location is not null ensure that the it will return true if the
	 * location is of the room type else false
	 * 
	 * @param location
	 *            - places on the board
	 * @param roomType
	 *            - room that the place might be in
	 * @return - boolean relating to the location.
	 */
	public boolean isRoom(Location location, char roomType) {
		char place = getCharAtLocation(location);
		if (roomType == place)
			return true;
		return false;
	}

	/**
	 * Checks if the location is a outer wall
	 * 
	 * @param location
	 *            - location to be checked.
	 * @return - returns true if the place is a outerwall otherwise false.
	 */
	public boolean isOutterWall(Location location) {
		char place = getCharAtLocation(location);
		if (place == 'Q') {
			return true;
		}
		return false;
	}

	/**
	 * checks if the location is a floor space
	 * 
	 * @param location
	 *            - location to be checked.
	 * @return - true if the loction is a floor false otherwise
	 */
	public boolean isFloor(Location location) {
		char place = getCharAtLocation(location);
		if (place == 'n') {
			return true;
		}
		return false;
	}

	/**
	 * checks if the location is a spawn point
	 * 
	 * @param location
	 *            - location to be checked
	 * @return - return true if the location is a spwan false otherwise
	 */
	public boolean isSpawn(Location location) {
		char place = getCharAtLocation(location);
		if (place == spawnWhite || place == spawnPeacock
				|| place == spawnMustard || place == spawnScarlet
				|| place == spawnPlum || place == spawnGreen) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a location is a door.
	 * 
	 * @param location
	 *            - location to be checked.
	 * @return - returns true if the location is on a door false otherwise.
	 */
	public boolean isDoor(Location location) {
		char place = getCharAtLocation(location);
		if (place == northDoor || place == southDoor || place == eastDoor
				|| place == westDoor) {
			return true;
		}
		return false;
	}

	/**
	 * used to get the char at a board location. handles logical screw up during
	 * load in keeps it from spreding to other classes
	 * 
	 * @param location
	 *            - location that is matched to a char
	 * @return - returns char at a location on the board.
	 */
	private char getCharAtLocation(Location location) {
		return board[location.y][location.x];
	}

	/**
	 * get the player whos turn it is.
	 * 
	 * @return - returns the player whos turn it is.
	 */
	public Player getTurnPlayer() {
		return turn.peek();
	}

	/**
	 * end the trun of the player who's turn it is.
	 */
	public void endTurn() {
		Player player = turn.poll();
		player.setTurn();
		turn.offer(player);
		turn.peek().setTurn();
	}

	/**
	 * checks of the game has been started.
	 * 
	 * @return
	 */
	public boolean hasStarted() {
		return hasStarted;
	}

	/**
	 * Checks if the game is over.
	 * 
	 * @return
	 */
	public boolean isGameOver() {
		return gameOver;
	}

	/**
	 * handles accusations if they are wrong the player is out of the game if he
	 * was the last player the game is over if they win the game is over
	 * 
	 * @param accuse
	 *            - a accusation being made by a player.
	 */
	public void handleAccuse(Accuse accuse) {
		if (solution.checkAccuse(accuse)) {
			JOptionPane.showMessageDialog(null, "You Win!!");
			gameOver = true;
		}
		JOptionPane.showMessageDialog(null, "You Lose and have can't play");
		turn.poll();
		if (turn.isEmpty()) {
			gameOver = true;
			return;
		}
		turn.peek().setTurn();
		Main.canvas.repaint();
	}
	
	//==================Door checking=====================//
	public boolean isNorthDoor(Location location){
		return getCharAtLocation(location) == northDoor;
	}
	public boolean isSouthDoor(Location location){
		return getCharAtLocation(location) == southDoor;
	}
	public boolean isEastDoor(Location location){
		return getCharAtLocation(location) == eastDoor;
	}
	public boolean isWestDoor(Location location){
		return getCharAtLocation(location) == westDoor;
	}

}
