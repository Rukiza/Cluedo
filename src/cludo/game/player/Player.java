package cludo.game.player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

import cludo.Main;
import cludo.game.cards.*;
import cludo.util.Dice;
import cludo.util.Location;
import cludo.util.Move;
import cludo.util.PathFinder;

public class Player implements MouseListener {

	private String name;
	private Character character;
	private Location location;
	private boolean isTurn;
	private Dice dice;
	private Hand hand = new Hand();
	public List<Location> currentPath;
	private int currentMoveAmount = 0;
	private boolean hasRolled;
	private boolean isInRoom;

	/**
	 * Constructs the player saving the details that are passed in.
	 * 
	 * @param name
	 *            - name of the player
	 * @param character
	 *            - the character they are playing as.
	 * @param location
	 *            - the location that they will start in.
	 * @param dice
	 *            - the dice the this game is using to roll with.
	 */
	public Player(String name, Character character, Location location, Dice dice) {
		this.name = name;
		this.character = character;
		this.location = location;
		this.dice = dice;
	}

	public String getName() {
		return name;
	}

	public void rollDice() {
		if (!hasRolled) {
			currentMoveAmount = dice.roll();
			Main.canvas.repaint();
			hasRolled = !hasRolled;
		}

	}

	public Hand getHand() {
		return hand;
	}

	/**
	 * returns the current location of the player on the board.
	 * 
	 * @return - the location of the player.
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * Sets the players turn just nots what was already there.
	 */
	public void setTurn() {
		isTurn = !isTurn;
	}

	/**
	 * returns if it is the players turn or not
	 * 
	 * @return - isTurn
	 */
	public boolean isTurn() {
		return isTurn;
	}

	/**
	 * returns the character the player is playing.
	 * 
	 * @return
	 */
	public String getCharacterName() {
		return character.name;
	}

	/**
	 * Player gets delt a card this is how he recives it.
	 * 
	 * @param card
	 *            - the card the player was delt.
	 */
	public void reciveCard(Card card) {
		hand.recive(card);
	}

	/**
	 * Make a request to move the player.
	 * 
	 * @param newLocation
	 *            - the new location that the player wishes to move to.
	 */
	private void move(Location newLocation) {
		int pathInclution = 1;
		if (currentPath != null && currentPath.size() - pathInclution <= currentMoveAmount) {
			if (Move.moveToRoom(location, newLocation)){isInRoom = true;System.out.println(isInRoom);}
			location = newLocation;
			currentMoveAmount -= currentPath.size() - pathInclution;
		} else {
			currentPath = null;
		}
	}
	
	public int getCurrentMove() {
		return currentMoveAmount;
	}

	public BufferedImage getPortrait() {
		return character.image;
	}

	// =============MouseEventHandling=================//
	@Override
	public void mouseReleased(MouseEvent e) {
		if (isTurn) {
			Location newLocation = new Location(e.getPoint());
			PathFinder path = new PathFinder();
			currentPath = path.findPath(location, newLocation);
			move(newLocation);
			Main.canvas.repaint();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
