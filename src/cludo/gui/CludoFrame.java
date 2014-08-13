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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import cludo.game.cards.Card;
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
	public CludoFrame(String title, CludoCanvas canvas, CludoBoard board) {
		super(title);

		this.setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		setLayout(new BorderLayout());
		JMenuBar bar = new JMenuBar();
		add(bar, BorderLayout.NORTH);
		bar.add(new JMenu("LOL"));

		JToolBar eventBar = new JToolBar();
		add(eventBar, BorderLayout.SOUTH);
		addButtonToToolBar(eventBar);

		this.board = board;
		this.canvas = canvas;

		this.add(canvas, BorderLayout.CENTER);
		this.setSize(1000, 720);
		this.setVisible(true);

	}

	private void addButtonToToolBar(JToolBar eventBar) {
		JButton button = new JButton("Accuse");
		eventBar.add(button);
		button = new JButton("Saggest");
		eventBar.add(button);
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals("Saggest")) {
					System.out.println("um am i getting here");
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
		JDialog pane = new JDialog();

		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JComboBox<Card> character = new JComboBox<Card>();
		character.addItem(new Card(Card.Type.CHARACTER, "MissScarlett"));
		character.addItem(new Card(Card.Type.CHARACTER, "MrsWhite"));
		character.addItem(new Card(Card.Type.CHARACTER, "MrsPeacock"));
		character.addItem(new Card(Card.Type.CHARACTER, "ReverandGreen"));
		character.addItem(new Card(Card.Type.CHARACTER, "ColonelMustard"));
		character.addItem(new Card(Card.Type.CHARACTER, "ProfessorPlum"));

		panel.add(character);

		JComboBox<Card> weapon = new JComboBox<Card>();
		weapon.addItem(new Card(Card.Type.WEAPON, "Spanner"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Dagger"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Rope"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Candlestick"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Revolver"));
		weapon.addItem(new Card(Card.Type.WEAPON, "Leadpipe"));

		panel.add(weapon);

		pane.add(panel);
		pane.setSize(300, 100);
		pane.setVisible(true);
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
