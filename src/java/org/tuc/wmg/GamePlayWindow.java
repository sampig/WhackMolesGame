package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePlayWindow extends JPanel {

	private static final long serialVersionUID = -802653897422611115L;

	private JFrame frame;
	private JPanel infoPane, playPane, buttonPane;
	private JLabel infoLabel;
	private ServerUI server;

	private List<JLabel> listLabelMoles = new ArrayList<>(0);

	public GamePlayWindow(ServerUI server) {
		super.setLayout(new BorderLayout());
		this.server = server;
		//
		infoPane = new JPanel();
		infoLabel = this.createInfoLabel();
		infoPane.add(infoLabel);
		//
		playPane = new JPanel();
		initGamepane();
		//
		buttonPane = new JPanel();
		add(infoPane, BorderLayout.NORTH);
		add(playPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
		setSize(400, 400);
	}

	public void startShow(Frame owner) {
		frame = new JFrame();
		frame.getContentPane().add(this);
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		int x = owner.getX() + (owner.getWidth() - this.getWidth()) / 2;
		int y = owner.getY() + (owner.getHeight() - this.getHeight()) / 2;
		this.setLocation(x, y);
		this.setVisible(true);
		frame.setVisible(true);
	}
	
	public void close() {
		if(frame!=null) {
			frame.setVisible(false);
		}
	}

	public JLabel createInfoLabel() {
		JLabel label = new JLabel();
		StringBuffer sb = new StringBuffer();
		GameLevel level = server.getLevel();
		sb.append("Total times: " + level.getTotalTimes());
		label.setText(sb.toString());
		return label;
	}

	public void initGamepane() {
		listLabelMoles.clear();
		playPane.removeAll();
		GridLayout layout = new GridLayout(0, 3);
		playPane.setLayout(layout);
		int num = server.getLevel().getNumMoles();
		for (int i = 0; i < num; i++) {
			java.net.URL imgURL = GameStatusPane.class.getClassLoader()
					.getResource("resources/images/circle_red_48px.png");
			ImageIcon iconRed = new ImageIcon(imgURL);
			JLabel label = new JLabel("Mole." + i, JLabel.CENTER);
			label.setIcon(iconRed);
			label.setVerticalTextPosition(JLabel.BOTTOM);
			label.setHorizontalTextPosition(JLabel.CENTER);
			listLabelMoles.add(label);
			playPane.add(label);
		}
		playPane.revalidate();
		playPane.repaint();
	}

	public void freshGamepane(int num, boolean hit) {
		for (int i = 0; i < listLabelMoles.size(); i++) {
			JLabel label = listLabelMoles.get(i);
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

}
