package cludo;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import cludo.gui.CludoBoard;
import cludo.gui.CludoCanvas;
import cludo.gui.CludoFrame;
import cludo.game.*;
import cludo.game.player.Character;
import cludo.game.player.Player;
import cludo.util.Dice;
import cludo.util.Move;

public class Main {

	public static CludoCanvas canvas;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		CludoBoard board = new CludoBoard(Main.class.getResourceAsStream("CludoGameBoard.txt"));
		Dice dice = new Dice();
		CludoCanvas canvas = new CludoCanvas(board, dice);
		CludoFrame frame = new CludoFrame("Cludo", canvas, board);
		Main.canvas = canvas;
		playerCreation(frame, canvas, board, dice);
		Move moveRules = new Move(board);
	}

	/**
	 * Handles PlayerCreation Makes a Jdialog box and adds a jpanel to it sets
	 * the layout manager for the jpanel.
	 * 
	 * @param frame
	 *            - The frame of the game that the players are going to be added
	 *            to.
	 */
	public static void playerCreation(final CludoFrame frame,
			final CludoCanvas canvas, final CludoBoard board, final Dice dice) {
		final JDialog playerForm = new JDialog(frame, "Start Game");
		final List<Player> playerList = new ArrayList<Player>();
		playerForm.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

		playerForm.setFocusable(true);
		JPanel formContent = new JPanel();

		// Constructing Layout orgnisation with GroupLayout.
		GroupLayout layout = new GroupLayout(formContent);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		// adds the JPanel and sets size of the PlayerForm
		playerForm.add(formContent);
		playerForm.setSize(300, 200);

		// Makes the name textField
		JTextField name = new JTextField("Name: ");
		name.setEditable(false);
		final JTextField playerName = new JTextField();
		playerName.setSize(15, 20);
		playerName.setColumns(15);

		// Constructing the radio buttons for character choices.
		// Sets ActionCommands aswell.
		JRadioButton scarlet = new JRadioButton("Miss Scarlet");
		scarlet.setActionCommand("Miss Scarlet");
		JRadioButton plum = new JRadioButton("Professor Plum");
		plum.setActionCommand("Professor Plum");
		JRadioButton green = new JRadioButton("Reverend Green");
		green.setActionCommand("Reverand Green");
		JRadioButton white = new JRadioButton("Mrs White");
		white.setActionCommand("Mrs White");
		JRadioButton mustard = new JRadioButton("Colonel Mustard");
		mustard.setActionCommand("Colonel Mustard");
		JRadioButton peacock = new JRadioButton("Mrs Peacock");
		peacock.setActionCommand("Mrs Peacock");

		// assigning them to a button group.
		final ButtonGroup characters = new ButtonGroup();
		characters.add(scarlet);
		characters.add(plum);
		characters.add(green);
		characters.add(white);
		characters.add(mustard);
		characters.add(peacock);

		// Sets up JButtons.
		JButton create = new JButton("Create");
		create.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if ("Create".equals(e.getActionCommand())) {
					String n = playerName.getText();
					if (!n.equals("")) {
						ButtonModel selected = characters.getSelection();
						characters.clearSelection();
						if (selected != null) {
							String selectedCharacter = selected
									.getActionCommand();
							System.out.println(n + "  " + selectedCharacter);
							playerList.add(new Player(n, new Character(
									selectedCharacter), board
									.findSpawn(selectedCharacter), dice));
							selected.setEnabled(false);
							playerName.setText("");
						}
						
					}
				}
			}
		});
		JButton finish = new JButton("Finished");
		finish.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if ("Finished".equals(e.getActionCommand())) {
					if (playerList.size() < 3) {
						return;
					}
					canvas.setPlayers(playerList);
					board.startGame(playerList);
					canvas.repaint();
					playerForm.dispose();

				}

			}
		});

		// Orginises the group layout.
		layout.setHorizontalGroup(layout
				.createSequentialGroup()
				.addComponent(name)
				.addComponent(playerName)
				.addGroup(
						layout.createParallelGroup(
								GroupLayout.Alignment.LEADING)
								.addComponent(scarlet).addComponent(plum)
								.addComponent(green).addComponent(white)
								.addComponent(mustard).addComponent(peacock)
								.addComponent(create).addComponent(finish)));
		layout.setVerticalGroup(layout
				.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(name)
				.addComponent(playerName)
				.addGroup(
						layout.createSequentialGroup().addComponent(scarlet)
								.addComponent(plum).addComponent(green)
								.addComponent(white).addComponent(mustard)
								.addComponent(peacock).addComponent(create)
								.addComponent(finish)));

		playerForm.setVisible(true);
	}
}
