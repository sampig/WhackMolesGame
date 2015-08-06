package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * @author Chenfeng Zhu
 *
 */
public class GameStatusPane extends JPanel {

	private static final long serialVersionUID = 1935065644500091308L;

	private ServerUI server;
	private JLabel sourceInfo;
	private JPanel sourcePane, gamePane;
	private JScrollPane infoPane;
	private JTextArea infoTextArea;

	private List<JLabel> listMoles = new ArrayList<>(0);

	public GameStatusPane(final ServerUI server) {
		this.server = server;
		sourceInfo = new JLabel();
		sourceInfo.setText(server.getSource());
		sourcePane = new JPanel();
		sourcePane.setBorder(BorderFactory
				.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Comm Info:"),
						BorderFactory.createEmptyBorder(1, 1, 1, 1)), sourcePane.getBorder()));
		sourcePane.add(sourceInfo);
		gamePane = new JPanel();
		gamePane.setPreferredSize(new Dimension(250, 250));
		gamePane.setBorder(BorderFactory
				.createCompoundBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Game Stat:"),
						BorderFactory.createEmptyBorder(1, 1, 1, 1)), gamePane.getBorder()));
		initGamepane();
		infoTextArea = new JTextArea();
		infoTextArea.setRows(10);
		infoPane = new JScrollPane(infoTextArea);
		infoPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		infoPane.setPreferredSize(new Dimension(350, 300));
		infoPane.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Information:"),
						BorderFactory.createEmptyBorder(1, 1, 1, 1)),
				infoPane.getBorder()));
		setLayout(new BorderLayout());
		add(sourcePane, BorderLayout.NORTH);
		add(infoPane, BorderLayout.CENTER);
		add(gamePane, BorderLayout.SOUTH);
	}

	/**
	 * Update the source.
	 * 
	 * @param text
	 */
	public void updateSourceInfo(String text) {
		sourceInfo.setText(text);
	}

	/**
	 * Initialize.
	 */
	public void initGamepane() {
		listMoles.clear();
		gamePane.removeAll();
		GridLayout layout = new GridLayout(0, 3);
		gamePane.setLayout(layout);
		int num = server.getLevel().getNumMoles();
		for (int i = 0; i < num; i++) {
			java.net.URL imgURL = GameStatusPane.class.getClassLoader()
					.getResource("resources/images/circle_red_48px.png");
			ImageIcon iconRed = new ImageIcon(imgURL);
			JLabel label = new JLabel("Mole." + i, JLabel.CENTER);
			label.setIcon(iconRed);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			listMoles.add(label);
			gamePane.add(label);
		}
		gamePane.revalidate();
		gamePane.repaint();
	}

	/**
	 * Refresh the game panel.
	 * 
	 * @param num
	 * @param hit
	 */
	public void refreshGamepane(int num, boolean hit) {
		for (int i = 0; i < listMoles.size(); i++) {
			JLabel label = listMoles.get(i);
			if (i == num) {
				if (hit) {
					label.setText("<<< Mole." + i + " >>>");
					java.net.URL imgURL = GameStatusPane.class.getClassLoader()
							.getResource("resources/images/circle_green_48px.png");
					ImageIcon iconBlue = new ImageIcon(imgURL);
					label.setIcon(iconBlue);
				} else {
					label.setText("*** Mole." + i + " ***");
					java.net.URL imgURL = GameStatusPane.class.getClassLoader()
							.getResource("resources/images/circle_green_48px.png");
					ImageIcon iconGreen = new ImageIcon(imgURL);
					label.setIcon(iconGreen);
				}
			} else {
				label.setText("Mole." + i);
				java.net.URL imgURL = GameStatusPane.class.getClassLoader()
						.getResource("resources/images/circle_red_48px.png");
				ImageIcon iconRed = new ImageIcon(imgURL);
				label.setIcon(iconRed);
			}
		}
	}

	/**
	 * Update the Console. Add new information.
	 * 
	 * @param text
	 */
	public void appendInfo(String text) {
		infoTextArea.append(text + "\n");
		infoPane.validate();
		infoPane.getVerticalScrollBar().setValue(infoPane.getVerticalScrollBar().getMaximum());
	}

	/**
	 * Clear the console.
	 */
	public void clearConsole() {
		infoTextArea.setText("");
	}

}
