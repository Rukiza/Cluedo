package cludo.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import cludo.game.Room;
import cludo.game.cards.Card;
import cludo.game.guess.Accuse;
import cludo.game.guess.Suggestion;
import cludo.game.player.Player;
import cludo.util.Dice;

public class CludoFrame extends JFrame implements WindowListener {

	private CludoCanvas canvas;
	private static CludoBoard board;

	/**
	 * Constructs the frame that will hold the canvas
	 * 
	 * @param title
	 *            - the name at the top of the frame
	 * @param canvas
	 *            - the place that the game will be drawn.
	 */
	public CludoFrame(String title, final CludoCanvas canvas,
			final CludoBoard board) {
		super(title);

		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setLayout(new BorderLayout());
		JMenuBar bar = new JMenuBar();
		add(bar, BorderLayout.NORTH);
		JMenu menu = new JMenu("Game");
		JMenuItem item = new JMenuItem("Restart");
		item.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				board.restart(CludoFrame.this, canvas);

			}
		});
		menu.add(item);
		bar.add(menu);

		JToolBar eventBar = new JToolBar();
		add(eventBar, BorderLayout.SOUTH);
		addButtonToToolBar(eventBar);

		this.board = board;
		this.canvas = canvas;

		this.add(canvas, BorderLayout.CENTER);
		this.setSize(1000, 720);
		this.setResizable(false);
		this.setVisible(true);

	}

	private void addButtonToToolBar(JToolBar eventBar) {
		JButton button = new JButton("Accuse");
		eventBar.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Accuse")) {
					if (board.isGameOver()) {
						JOptionPane.showMessageDialog(null, "Game is Over");
						return;
					}
					accuse();
				}

			}
		});

		button = new JButton("Suggest");
		eventBar.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Suggest")) {
					if (board.isGameOver()) {
						JOptionPane.showMessageDialog(null, "Game is Over");
						return;
					}
					CludoFrame.saggest();
				}
			}
		});

		button = new JButton("Roll Dice");
		eventBar.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (e.getActionCommand().equals("Roll Dice")) {
					if (!board.hasStarted()) {
						JOptionPane.showMessageDialog(null,
								"Wait for the game to start");
						return;
					}
					if (board.isGameOver()) {
						JOptionPane.showMessageDialog(null, "Game is Over");
						return;
					}
					Player player = board.getTurnPlayer();
					player.rollDice();
				}
			}
		});

		button = new JButton("End Turn");
		eventBar.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("End Turn")) {
					if (!board.hasStarted()) {
						JOptionPane.showMessageDialog(null,
								"Wait for the game to start");
						return;
					}
					if (board.isGameOver()) {
						JOptionPane.showMessageDialog(null, "Game is Over");
						return;
					}
					if (board.hasStarted()) {
						int option = JOptionPane
								.showConfirmDialog(
										CludoFrame.this,
										new JLabel(
												"Are you sure you wish to end your turn?"),
										"Confirm end of turn",
										JOptionPane.YES_NO_OPTION,
										JOptionPane.QUESTION_MESSAGE);
						if (option == JOptionPane.YES_NO_OPTION) {
							board.endTurn();
							repaint();
						}
					}
				}
			}
		});
	}

	private static void saggest() {
		final JDialog pane = new JDialog();

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		if (!board.hasStarted()) {
			JOptionPane.showMessageDialog(null, "Wait for the game to start");
			return;
		}
		final Room currentRoom = board.getRoomPlayerIsIn(board.getTurnPlayer());
		if (currentRoom == null) {
			JOptionPane.showMessageDialog(null,
					"You  need to be in a room to suggestion");
			return;
		}
		JLabel room = new JLabel(currentRoom.getCard().toString());
		panel.add(room);

		final JComboBox<Card> character = getCharacters();

		panel.add(character);

		final JComboBox<Card> weapon = getWeapons();

		panel.add(weapon);

		JButton button = new JButton("Suggestion");
		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Suggestion")) {
					Card roomChosen = currentRoom.getCard();
					Card charactersChosen = (Card) character.getSelectedItem();
					Card weaponChosen = (Card) weapon.getSelectedItem();
					Suggestion sug = new Suggestion(roomChosen, weaponChosen,
							charactersChosen);
					pane.dispose();
					board.handleSuggestion(sug);
				}
			}
		});

		pane.setLocationRelativeTo(null);
		pane.add(panel);
		pane.pack();

		pane.setVisible(true);

	}

	private static void accuse() {
		final JDialog pane = new JDialog();

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		if (!board.hasStarted()) {
			JOptionPane.showMessageDialog(null, "Wait for the game to start");
			return;
		}
		final JComboBox<Card> rooms = getRooms();
		final JComboBox<Card> weapons = getWeapons();
		final JComboBox<Card> characters = getCharacters();

		panel.add(rooms);

		panel.add(characters);

		panel.add(weapons);

		JButton button = new JButton("Suggestion");
		panel.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Card roomChosen = (Card) rooms.getSelectedItem();
				Card charactersChosen = (Card) characters.getSelectedItem();
				Card weaponChosen = (Card) weapons.getSelectedItem();
				Accuse accuse = new Accuse(roomChosen, charactersChosen,
						weaponChosen);
				pane.dispose();
				board.handleAccuse(accuse);
			}
		});

		pane.setLocationRelativeTo(null);
		pane.add(panel);
		pane.pack();

		pane.setVisible(true);

	}

	private static JComboBox<Card> getWeapons() {
		JComboBox<Card> weapon = new JComboBox<Card>();
		weapon.addItem(new Card(Card.Type.WEAPON, "Spanner"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Dagger"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Rope"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Candlestick"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Revolver"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Leadpipe"));
		return weapon;
	}

	private static JComboBox<Card> getRooms() {
		JComboBox<Card> room = new JComboBox<Card>();
		room.addItem(new Card(Card.Type.ROOM, "Kitchen"));
		room.addItem(new Card(Card.Type.ROOM, "Ballroom"));
		room.addItem(new Card(Card.Type.ROOM, "Conservatory"));
		room.addItem(new Card(Card.Type.ROOM, "BilliardRoom"));
		room.addItem(new Card(Card.Type.ROOM, "Library"));
		room.addItem(new Card(Card.Type.ROOM, "Study"));
		room.addItem(new Card(Card.Type.ROOM, "Lounge"));
		room.addItem(new Card(Card.Type.ROOM, "Hall"));
		room.addItem(new Card(Card.Type.ROOM, "DiningRoom"));
		return room;
	}

	private static JComboBox<Card> getCharacters() {
		JComboBox<Card> character = new JComboBox<Card>();
		character.addItem(new Card(Card.Type.CHARACTER, "MissScarlett"));
		character.addItem(new Card(Card.Type.CHARACTER, "MrsWhite"));
		character.addItem(new Card(Card.Type.CHARACTER, "MrsPeacock"));
		character.addItem(new Card(Card.Type.CHARACTER, "ReverandGreen"));
		character.addItem(new Card(Card.Type.CHARACTER, "ColonelMustard"));
		character.addItem(new Card(Card.Type.CHARACTER, "ProfessorPlum"));
		return character;
	}

	public void windowClosing(WindowEvent e) {
		int option = JOptionPane.showConfirmDialog(CludoFrame.this, new JLabel(
				"Exiting Cluedo"), "Confirm Exit", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (option == JOptionPane.YES_NO_OPTION) {
			System.exit(0);
		}
	}

	@Override
	public void repaint() {
		canvas.repaint();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowActivated(WindowEvent e) {
	}

	public void windowDeactivated(WindowEvent e) {
	}

}
