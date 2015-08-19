package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tuc.wmg.utils.MoteInfo;
import org.tuc.wmg.utils.Constants.MoleStatus;

public class GamePlayWindow extends JPanel {

	private static final long serialVersionUID = -802653897422611115L;

	private JFrame frame;
	private JPanel infoPane, playPane, buttonPane;
	private JLabel infoLabel;
	private JButton stopBtn;
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
		initPlayPanel();
		//
		buttonPane = new JPanel();
		stopBtn = new JButton("Stop");
		buttonPane.add(stopBtn);
		add(infoPane, BorderLayout.NORTH);
		add(playPane, BorderLayout.CENTER);
		add(buttonPane, BorderLayout.SOUTH);
		setSize(400, 400);
	}

	/**
	 * 
	 * @param owner
	 */
	public void startShow(Frame owner) {
		frame = new JFrame();
		frame.getContentPane().add(this);
		// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		int x = owner.getX() + (owner.getWidth() - this.getWidth()) / 2;
		int y = owner.getY() + (owner.getHeight() - this.getHeight()) / 2;
		this.setLocation(x, y);
		this.setVisible(true);
		frame.setVisible(true);
	}

	/**
	 * Close the game frame.
	 */
	public void close() {
		if (frame != null) {
			frame.setVisible(false);
		}
	}

	/**
	 * Create the information Label.
	 * 
	 * @return
	 */
	public JLabel createInfoLabel() {
		JLabel label = new JLabel();
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("Total times: " + server.getLevel().getTotalTimes() + "<br/>");
		sb.append("Current times: " + 0 + "<br/>");
		sb.append("Hitting times: " + 0 + "<br/>");
		sb.append("Result: " + 0 + "<br/>");
		sb.append("Pressing time: " + 0 + "<br/>");
		sb.append("</html>");
		label.setText(sb.toString());
		return label;
	}

	/**
	 * Initialize the Play Panel.
	 */
	public void initPlayPanel() {
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

	/**
	 * Update the information Label.
	 */
	public void updateInfoLabel() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("Total times: " + server.getCurrentGame().getTotalTimes() + "<br/>");
		sb.append("Current times: " + server.getCurrentGame().getCurrentTimes() + "<br/>");
		sb.append("Hitting times: " + server.getCurrentGame().getHitTimes() + "<br/>");
		sb.append("Result: " + (1.0 * server.getCurrentGame().getHitTimes() / server.getCurrentGame().getCurrentTimes())
				+ "<br/>");
		sb.append("Pressing time: " + server.getCurrentGame().getTotalPressedTime() + "<br/>");
		sb.append("</html>");
		infoLabel.setText(sb.toString());
	}

	/**
	 * Refresh the Play Panel.
	 * 
	 * @param num
	 * @param hit
	 */
	public void refreshPlayPanel(int mid, MoleStatus status) {
		for (int i = 0; i < listLabelMoles.size(); i++) {
			JLabel label = listLabelMoles.get(i);
			MoteInfo mote = server.getAvailableMoles().get(i);
			if (mote.getMid() == mid) {
				java.net.URL imgURL;
				switch (status) {
				case OUT:
					label.setText("*** " + i + mote + " ***");
					imgURL = GamePlayWindow.class.getClassLoader().getResource("resources/images/mole_48px.png");
					ImageIcon iconMole = new ImageIcon(imgURL);
					label.setIcon(iconMole);
					break;
				case HIT:
					label.setText("+++ " + i + mote + " +++");
					imgURL = GamePlayWindow.class.getClassLoader()
							.getResource("resources/images/circle_green_48px.png");
					ImageIcon iconGreen = new ImageIcon(imgURL);
					label.setIcon(iconGreen);
					break;
				case MISSING:
					label.setText("--- " + i + mote + " ---");
					imgURL = GamePlayWindow.class.getClassLoader().getResource("resources/images/circle_blue_48px.png");
					ImageIcon iconBlue = new ImageIcon(imgURL);
					label.setIcon(iconBlue);
					break;
				}
			} else {
				label.setText("Mole." + i);
				java.net.URL imgURL = GamePlayWindow.class.getClassLoader()
						.getResource("resources/images/circle_red_48px.png");
				ImageIcon iconRed = new ImageIcon(imgURL);
				label.setIcon(iconRed);
			}
		}
	}

}
