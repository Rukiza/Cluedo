package cludo.game.player;

import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

import cludo.Main;
import cludo.game.cards.*;
import cludo.game.guess.Suggestion;
import cludo.util.*;

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
	private boolean isRefuting;
	private boolean hasSuggested;

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

	/**
	 * Updates the amount left to move.
	 * @param amountLeft
	 */
	public void updateMove(int amountLeft) {
		currentMoveAmount = amountLeft;
	}
	
	/**
	 * used to get the players name
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Rolls the dice and stores the total in the current move.
	 */
	public void rollDice() {
		if (!hasRolled) {
			currentMoveAmount = dice.roll();
			Main.canvas.repaint();
			hasRolled = !hasRolled;
		}

	}

	/**
	 * Use to get the players current hand.
	 * hand is a is itrable
	 * @return
	 */
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
		hasRolled = false;
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
		if (Move.makeMove(location, newLocation, this)) {
			location = newLocation;
		}
	}

	/**
	 * Player can refute a suggestion that is made by another player
	 * Supply the interface for this.
	 * if for some reson the player cant refute then the it will call the 
	 * show suggestion method.
	 * @param suggestion - suggestion that the player whos turn it is has made.
	 * @param player - player that made the suggestion
	 * @return returns the card that is used to refute or null if can't;
	 */
	public Card refute(Suggestion suggestion, Player player) {
		// uses method from the suggestion class that checks the hand for avalible cards.
		isRefuting = true;
		List<Card> cardsCanRefuteWith = suggestion.refute(hand);
		if (!cardsCanRefuteWith.isEmpty()) {
			// constructs the displaye for the infomation.
			JPanel panel = new JPanel();
			JComboBox<Card> combo = new JComboBox<Card>();
			for (Card c : cardsCanRefuteWith) {
				combo.addItem(c);
			}
			panel.add(combo);
			JOptionPane.showMessageDialog(null, panel,
					player.getName()+" made a Suggestion please refute",
					JOptionPane.NO_OPTION);
			// returns the card that the p[layer wishes to show the player who suggested.
			isRefuting = false;
			return (Card)combo.getSelectedItem();
		}
		// becasue the player couldent refute this call the showSuggestion method inside player
		// that will handle this situation.
		showSuggestion(suggestion, player);
		isRefuting = false;
		return null;
	}
	
	/**
	 * Used inplace of the refute menu just displase the cards that the player suggested
	 * @param suggestion - cards that where suggested
	 * @param player - player that suggested them.
	 */
	public void showSuggestion(Suggestion suggestion, Player player) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		for (Card c: suggestion){
			panel.add(new JLabel(c.toString()+" "));
		}
		JOptionPane.showMessageDialog(null, panel, player.getName()+" made a Suggestion", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Shows the message for if the player that contains the the card used to refute with
	 * @param card	- card that is being used to refute there suggestion
	 * @param player - player that is refuting them.
	 */
	public void showRefute(Card card, Player player) {
		if (player == null){
			JOptionPane.showMessageDialog(null, "No players have refuted you", "Refute", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		panel.add(new JLabel(card.toString()+" "));
		JOptionPane.showMessageDialog(null, panel, player.getName()+" refuted you with:", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Gets the move amount avalible to the player
	 * @return
	 */
	public int getCurrentMove() {
		return currentMoveAmount;
	}

	/**
	 * Gets the protrat image of the character the player is playing
	 * @return
	 */
	public BufferedImage getPortrait() {
		return character.image;
	}

	/**
	 * Tell the if the player is refuting or not.
	 * @return
	 */
	public boolean isRefuting() {
		return isRefuting;
	}

	/**
	 * Set if the player is refuting or not.
	 * @param isRefuting
	 */
	public void setRefuting(boolean isRefuting) {
		this.isRefuting = isRefuting;
	}
	
	/**
	 * Used in the suggest implementation to stop a player form suggesting more than once per turn.
	 * @return - if they have suggested this turn
	 */
	public boolean isHasSuggested() {
		return hasSuggested;
	}
	
	/**
	 * Used to change the suggest boolean once the players turn is up or they have suggest something.
	 * @param hasSuggested - the value that it is to be swapped to.
	 */
	public void setHasSuggested(boolean hasSuggested) {
		this.hasSuggested = hasSuggested;
	}
	
	/**
	 * returns if the player has rolled or not
	 * @return
	 */
	public boolean hasRolled(){
		return true;
	}
	
	/**
	 * setting for the location avoid using except for secret passages.
	 * @param location
	 */
	public void setLocation(Location location){
		this.location = location;
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

	// unused methods.
	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	

}
