package cludo.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JPanel;

import cludo.game.cards.Card;
import cludo.game.cards.Hand;
import cludo.game.player.Player;
import cludo.util.Dice;
import cludo.util.Location;

public class CludoCanvas extends JPanel {

	private CludoBoard board;
	private Dice dice;
	private BufferedImage image;
	public static final int squareSize = 25;
	public static boolean refuteDrawCase;

	public CludoCanvas(CludoBoard board, Dice dice) {
		super();
		this.setBackground(Color.black);
		this.board = board;
		this.dice = dice;
	}

	// List of players that are part of the game.
	private List<Player> playerList;

	// FloorColors
	private final Color floorColor = new Color(255, 215, 0);
	private final Color floorOutline = new Color(218, 165, 32);

	// PlayerColors
	private final Color mrsWhite = Color.white;
	private final Color mrsPeacock = new Color(0, 0, 205);
	private final Color colonelMustard = new Color(255, 255, 0);
	private final Color missScarlet = new Color(220, 20, 60);
	private final Color professorPlum = new Color(75, 0, 130);
	private final Color reverendGreen = new Color(154, 205, 50);

	// Colors for secret passages.
	private final Color secret1 = new Color(255, 99, 71);
	private final Color secret2 = new Color(255, 160, 122);
	private final Color arrowColor = new Color(128, 128, 0);

	// RoomColors
	private final Color room = new Color(143, 188, 143);
	private final Color walls = new Color(139, 69, 19);

	// Higlighted Floor colors
	private final Color floorColorHiglight = new Color(255, 255, 0);
	private final Color floorOutlineHighlight = new Color(188, 155, 32);

	// OuterWallColors
	private final Color outerWalls = new Color(128, 128, 128);
	private final Color outerOutline = new Color(169, 169, 169);

	// Door colors
	private final Color doorColor = new Color(120, 188, 140);
	private final Color doorOutline = new Color(120, 150, 140);

	// dice color
	private final Color diceColor = Color.white;
	private final Color diceFace = Color.black;

	// backgroundColor
	private final Color backGroundColor = Color.lightGray;

	// size of the dice.
	public static final int cardWidth = 75;
	public static final int cardHeight = 110;

	// dice Position
	private final int dicePositionX = 25 * squareSize + 3 * cardWidth;
	private final int dicePositionY = 16 * squareSize;
	private final int diceSize = 3 * squareSize;
	private final int diceNumberSize = 10; // devisable by 2

	// movment text
	private final int movementTextPositionX = dicePositionX + 10;
	private final int movementTextPositionY = 2 * diceSize + dicePositionY + 30
			+ 20;

	@Override
	public void paint(Graphics g) {
		g.setColor(backGroundColor);
		g.fillRect(0, 0, 1000, 720);
		for (int x = 0; x < board.getWidth(); x++) {
			for (int y = 0; y < board.getHeight(); y++) {
				Location location = new Location(x, y);
				g.setColor(Color.white);
				if (board.isOutterWall(location)) {
					// draws the outter walls.
					drawOuterWalls(x, y, g);
				} else if (board.isFloor(location)) {
					// call to a method that draws the Floor tiles.
					drawFloor(x, y, g);
				} else if (board.isRoom(location)) {
					// call to a method that draws the RoomTiles.
					drawRoom(x, y, g);
				} else if (board.isDoor(location)) {
					// draws the doors
					drawDoor(x, y, g);
				} else if (board.isSpawn(location)) {
					// call to a method that draws the spawn tiles.
					drawSpawn(x, y, g);
				} else if (board.isSecretPassage(location)) {
					// calls the method that draws the secret passage ways.
					drawSecretPassage(location, g);
				}
				// calls draw player makes other calls to player only draws.
				drawPlayers(g);
				drawDice(g);
			}
		}
		g.dispose();
		// bufferStrat.show();
	}

	/**
	 * Draws the floor tiles
	 * 
	 * @param x
	 *            - the row the the tile is in.
	 * @param y
	 *            - the colum that the tile is in
	 * @param g
	 *            - the place the floor tile will be drawn
	 */
	private void drawFloor(int x, int y, Graphics g) {
		g.setColor(floorColor);
		g.fillRect(squareSize * x, squareSize * y, squareSize, squareSize);
		g.setColor(floorOutline);
		g.drawRect(squareSize * x, squareSize * y, squareSize, squareSize);
	}

	/**
	 * Draws the spawn points for all the Characters
	 * 
	 * @param x
	 *            - the row the spawn point is in.
	 * @param y
	 *            - the column the spawn point is in.
	 * @param g
	 *            - the place that the spawn point will be drawn
	 * @param spawn
	 *            - the color of the spawn.
	 */
	private void drawSpawn(int x, int y, Graphics g) {
		g.setColor(floorColor);
		g.fillRect(squareSize * x, squareSize * y, squareSize, squareSize);
		g.setColor(floorOutline);
		g.drawRect(squareSize * x, squareSize * y, squareSize, squareSize);
	}

	/**
	 * Draws the rooms.
	 * 
	 * @param x
	 *            - the row the room tile is on
	 * @param y
	 *            - the column the room tile is on.
	 * @param g
	 *            - the place the room tile will be drawn.
	 */
	private void drawRoom(int x, int y, Graphics g) {
		g.setColor(room);
		g.fillRect(squareSize * x, squareSize * y, squareSize, squareSize);
		g.setColor(walls);
		int distance = 24;
		Location south = new Location(x + 1, y);
		Location north = new Location(x - 1, y);
		if (x < 24 && x > 0) {
			if (board.isFloor(south) || board.isOutterWall(south)) {
				g.drawLine(squareSize * (x) + distance, squareSize * (y),
						squareSize * (x) + distance, squareSize * (y + 1));
			} else if (board.isFloor(north) || board.isOutterWall(north)) {
				g.drawLine(squareSize * (x), squareSize * (y),
						squareSize * (x), squareSize * (y + 1));
			}
		}
		Location east = new Location(x, y + 1);
		Location west = new Location(x, y - 1);

		if (y < 24 && y > 0) {
			if (board.isFloor(east) || board.isOutterWall(east)) {
				g.drawLine(squareSize * (x), squareSize * (y) + distance,
						squareSize * (x + 1), squareSize * (y) + distance);
			} else if (board.isFloor(west) || board.isOutterWall(west)) {
				g.drawLine(squareSize * (x), squareSize * (y), squareSize
						* (x + 1), squareSize * (y));
			}
		}
	}

	private void drawDoor(int x, int y, Graphics g) {
		g.setColor(doorColor);
		g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
		g.setColor(doorOutline);
		g.drawRect(x * squareSize, y * squareSize, squareSize, squareSize);
		g.setColor(walls);
		int distance = 24;
		Location east = new Location(x + 1, y);
		Location west = new Location(x - 1, y);
		Location south = new Location(x, y + 1);
		Location north = new Location(x, y - 1);
		if (x < 24 && x > 0) {
			if ((board.isFloor(east) || board.isOutterWall(east))
					&& !board.isWestDoor(new Location(x, y))) {
				g.drawLine(squareSize * (x) + distance, squareSize * (y),
						squareSize * (x) + distance, squareSize * (y + 1));
			} else if ((board.isFloor(west) || board.isOutterWall(west))
					&& !board.isEastDoor(new Location(x, y))) {
				g.drawLine(squareSize * (x), squareSize * (y),
						squareSize * (x), squareSize * (y + 1));
			}
		}

		if (y < 24 && y > 0) {
			if ((board.isFloor(south) || board.isOutterWall(south))
					&& !board.isSouthDoor(new Location(x, y))) {
				g.drawLine(squareSize * (x), squareSize * (y) + distance,
						squareSize * (x + 1), squareSize * (y) + distance);
			} else if ((board.isFloor(north) || board.isOutterWall(north))
					&& !board.isNorthDoor(new Location(x, y))) {
				g.drawLine(squareSize * (x), squareSize * (y), squareSize
						* (x + 1), squareSize * (y));
			}
		}
	}

	/**
	 * Draws the outter walls of the board.
	 * 
	 * @param x
	 *            - row that the wall tile is on
	 * @param y
	 *            - column that the wall tile is on
	 * @param g
	 *            - the place that the outter walls will be drawn.
	 */
	private void drawOuterWalls(int x, int y, Graphics g) {
		g.setColor(outerWalls);
		g.fillRect(squareSize * x, squareSize * y, squareSize, squareSize);
		g.setColor(outerOutline);
		g.drawRect(squareSize * x, squareSize * y, squareSize, squareSize);
	}

	/**
	 * Draws the player at there position. goes through the player list and gets
	 * there location then draws the player at that point.
	 * 
	 * @param g
	 *            - the place the character is to be drawn on.
	 */
	private void drawPlayers(Graphics g) {

		if (playerList != null) {
			for (Player p : playerList) {
				if (p.isTurn() && p.currentPath != null) {
					List<Location> path = p.currentPath;
					for (Location l : path) {
						g.setColor(floorColorHiglight);
						g.fillRect(l.x * squareSize, l.y * squareSize,
								squareSize, squareSize);
						g.setColor(floorOutlineHighlight);
						g.drawRect(l.x * squareSize, l.y * squareSize,
								squareSize, squareSize);
					}

				}
				if (p.isTurn() && !refuteDrawCase) {
					// called only if it is the players turn.
					drawPlayersHand(p, g);
					drawPlayerPortrait(p, g);
					drawPlayerMoveAmount(p, g);
				}
				if (refuteDrawCase && p.isRefuting()) {
					// drawPlayersHand(p, g);
					drawCoveredHand(p, g);
					drawPlayerPortrait(p, g);
					drawPlayerMoveAmount(p, g);
				}
				Location playerLocation = p.getLocation();
				g.setColor(getPlayerColor(p.getCharacterName()));
				g.fillOval(playerLocation.x * squareSize, playerLocation.y
						* squareSize, squareSize, squareSize);
				g.setColor(Color.black);
				g.drawOval(playerLocation.x * squareSize, playerLocation.y
						* squareSize, squareSize, squareSize);

			}
		}

	}

	private void drawCoveredHand(Player player, Graphics g) {
		g.setColor(getPlayerColor(player.getCharacterName()));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.drawString(player.getName() + "'s Hand", board.getWidth()
				* squareSize + 20, 30);
		Hand hand = player.getHand();
		int i = 0;
		int j = 0;
		g.setColor(backGroundColor);
		for (Card card : hand) {
			g.fillRect(board.getWidth() * squareSize + CludoCanvas.cardWidth
					* i, CludoCanvas.cardHeight * j + 50,
					CludoCanvas.cardWidth, CludoCanvas.cardHeight);
			i++;
			if (i % 5 == 0) {
				i = 0;
				j++;
			}
		}
	}

	/**
	 * Gets the color of the character that the player is playing as.
	 * 
	 * @param characterName
	 *            - the name of the character.
	 * @return returns the color that matches the name.
	 */
	private Color getPlayerColor(String characterName) {
		if ("Miss Scarlet".equals(characterName)) {
			return missScarlet;
		}
		if ("Colonel Mustard".equals(characterName)) {
			return colonelMustard;
		}
		if ("Mrs White".equals(characterName)) {
			return mrsWhite;
		}
		if ("Mrs Peacock".equals(characterName)) {
			return mrsPeacock;
		}
		if ("Professor Plum".equals(characterName)) {
			return professorPlum;
		} else {
			return reverendGreen;
		}
	}

	private void drawPlayerPortrait(Player player, Graphics g) {
		BufferedImage image = player.getPortrait();
		g.drawImage(image, board.getWidth() * squareSize, 11 * squareSize,
				null, null);
	}

	private void drawPlayersHand(Player player, Graphics g) {
		g.setColor(getPlayerColor(player.getCharacterName()));
		g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
		g.drawString(player.getName() + "'s Hand", board.getWidth()
				* squareSize + 20, 30);
		Hand hand = player.getHand();
		int i = 0;
		int j = 0;
		for (Card card : hand) {
			g.drawImage(card.getImage(), board.getWidth() * squareSize
					+ CludoCanvas.cardWidth * i, CludoCanvas.cardHeight * j
					+ 50, null, null);
			i++;
			if (i % 5 == 0) {
				i = 0;
				j++;
			}
		}
	}

	private void drawPlayerMoveAmount(Player player, Graphics g) {
		int currentMove = player.getCurrentMove();
		g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
		g.drawString("Movement Left: " + currentMove, movementTextPositionX,
				movementTextPositionY);
	}

	private void drawDice(Graphics g) {
		g.setColor(diceColor);
		int gap = 10;
		g.fillRect(dicePositionX + gap, dicePositionY, diceSize, diceSize);
		drawDiceFace(dicePositionX + gap, dicePositionY, g, dice.getRollOne());
		g.fillRect(dicePositionX + gap, dicePositionY + diceSize + gap,
				diceSize, diceSize);
		drawDiceFace(dicePositionX + gap, dicePositionY + diceSize + gap, g,
				dice.getRollTwo());

	}

	private void drawDiceFace(int x, int y, Graphics g, int roll) {
		g.setColor(diceFace);
		if (roll == 1) {
			drawDiceFaceOne(x, y, g);
		} else if (roll == 2) {
			drawDiceFaceTwo(x, y, g);
		} else if (roll == 3) {
			drawDiceFaceThree(x, y, g);
		} else if (roll == 4) {
			drawDiceFaceFour(x, y, g);
		} else if (roll == 5) {
			drawDiceFaceFive(x, y, g);
		} else if (roll == 6) {
			drawDiceFaceSix(x, y, g);
		}
		g.setColor(diceColor);
	}

	private void drawDiceFaceOne(int x, int y, Graphics g) {
		g.fillOval(x + diceSize / 2 - diceNumberSize / 2, y + diceSize / 2
				- diceNumberSize / 2, diceNumberSize, diceNumberSize);
	}

	private void drawDiceFaceTwo(int x, int y, Graphics g) {
		g.fillOval(x + diceSize / 4 - diceNumberSize / 2, y + diceSize / 4
				- diceNumberSize / 2, diceNumberSize, diceNumberSize);
		g.fillOval(x + (diceSize / 4) * 3 - diceNumberSize / 2, y
				+ (diceSize / 4) * 3 - diceNumberSize / 2, diceNumberSize,
				diceNumberSize);
	}

	private void drawDiceFaceThree(int x, int y, Graphics g) {
		drawDiceFaceOne(x, y, g);
		drawDiceFaceTwo(x, y, g);
	}

	private void drawDiceFaceFour(int x, int y, Graphics g) {
		drawDiceFaceTwo(x, y, g);
		g.fillOval(x + (diceSize / 4) * 3 - diceNumberSize / 2, y
				+ (diceSize / 4) - diceNumberSize / 2, diceNumberSize,
				diceNumberSize);
		g.fillOval(x + (diceSize / 4) - diceNumberSize / 2, y + (diceSize / 4)
				* 3 - diceNumberSize / 2, diceNumberSize, diceNumberSize);
	}

	private void drawDiceFaceFive(int x, int y, Graphics g) {
		drawDiceFaceFour(x, y, g);
		drawDiceFaceOne(x, y, g);
	}

	private void drawDiceFaceSix(int x, int y, Graphics g) {
		drawDiceFaceFour(x, y, g);
		g.fillOval(x + diceSize / 2 - diceNumberSize / 2, y + diceSize / 4
				- diceNumberSize / 2, diceNumberSize, diceNumberSize);
		g.fillOval(x + diceSize / 2 - diceNumberSize / 2, y + (diceSize / 4)
				* 3 - diceNumberSize / 2, diceNumberSize, diceNumberSize);
	}

	/**
	 * Handles drawing of secret passages.
	 * @param location - that the secret passage may be drawn
	 * @param g -  the place the secret passage will be drawn if the place is a secret passage.
	 */
	private void drawSecretPassage(Location location, Graphics g) {
		if (board.isSecretPassageOne(location)) {
			drawSecretPassageOne(location, g);
		} else {
			drawSecretPassageTwo(location, g);
		}

		g.setColor(arrowColor);

		if (location.x > (board.getWidth() / 2)) {
			if (location.y > (board.getHeight() / 2)) {
				g.drawLine(location.x * squareSize + 3, location.y * squareSize
						+ 3, location.x * squareSize + 20, location.y
						* squareSize + 20);
				g.drawLine(location.x * squareSize + 3, location.y * squareSize
						+ 3, location.x * squareSize + 20, location.y
						* squareSize + 3);
				g.drawLine(location.x * squareSize + 3, location.y * squareSize
						+ 3, location.x * squareSize + 3, location.y
						* squareSize + 20);
			} else {
				g.drawLine(location.x * squareSize + 3, location.y * squareSize
						+ squareSize - 3, location.x * squareSize + 20,
						location.y * squareSize + squareSize - 20);
				g.drawLine(location.x * squareSize + 3, location.y * squareSize
						+ squareSize - 3, location.x * squareSize + 20,
						location.y * squareSize + squareSize - 3);
				g.drawLine(location.x * squareSize + 3, location.y * squareSize
						+ squareSize - 3, location.x * squareSize + 3,
						location.y * squareSize + squareSize - 20);
			}
		} else {
			if (location.y > (board.getHeight() / 2)) {
				g.drawLine(location.x * squareSize + squareSize - 3, location.y
						* squareSize + 3, location.x * squareSize + squareSize
						- 20, location.y * squareSize + 20);
				g.drawLine(location.x * squareSize + squareSize - 3, location.y
						* squareSize + 3, location.x * squareSize + squareSize
						- 20, location.y * squareSize + 3);
				g.drawLine(location.x * squareSize + squareSize - 3, location.y
						* squareSize + 3, location.x * squareSize + squareSize
						- 3, location.y * squareSize + 20);
			} else {
				g.drawLine(location.x * squareSize + squareSize - 3, location.y
						* squareSize + squareSize - 3, location.x * squareSize
						+ squareSize - 20, location.y * squareSize + squareSize
						- 20);
				g.drawLine(location.x * squareSize + squareSize - 3, location.y
						* squareSize + squareSize - 3, location.x * squareSize
						+ squareSize - 20, location.y * squareSize + squareSize
						- 3);
				g.drawLine(location.x * squareSize + squareSize - 3, location.y
						* squareSize + squareSize - 3, location.x * squareSize
						+ squareSize - 3, location.y * squareSize + squareSize
						- 20);
			}
		}
	}

	private void drawSecretPassageTwo(Location location, Graphics g) {
		g.setColor(secret2);
		g.fillRect(location.x * squareSize, location.y * squareSize,
				squareSize, squareSize);
	}

	private void drawSecretPassageOne(Location location, Graphics g) {
		g.setColor(secret1);
		g.fillRect(location.x * squareSize, location.y * squareSize,
				squareSize, squareSize);
	}

	/**
	 * Sets the players and adds them to the MouseListners
	 * @param playerList
	 */
	public void setPlayers(List<Player> playerList) {
		this.playerList = playerList;
		for (Player player : playerList) {
			addMouseListener(player);
		}
	}

}
