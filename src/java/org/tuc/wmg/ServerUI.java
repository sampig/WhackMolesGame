/*
 * ServerUI - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

import org.tuc.wmg.menu.AboutDialog;
import org.tuc.wmg.menu.ControlMenuBar;
import org.tuc.wmg.menu.ControlToolBar;
import org.tuc.wmg.menu.OptionsDialog;

/**
 * ServerUI provides an interface to record the game.
 * 
 * @author Chenfeng Zhu
 *
 */
public class ServerUI extends JPanel {

	private static final long serialVersionUID = -6911094015780609780L;

	private String title = "Whack Moles Game";
	private boolean isRunning = false;

	private JSplitPane mainPane;
	private GameRankPane rankPane;
	private GameStatusPane statusPane;
	private JLabel statusBar;

	private GameLevel level = GameLevel.LEVEL_BEGINNER;

	private GameThread game;
	private String source = "serial@/dev/ttyUSB0:115200";
	private String playerName;

	private PhoenixSource phoenixSource;

	public ServerUI() {
		rankPane = new GameRankPane(this);
		statusPane = new GameStatusPane(this);
		mainPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, rankPane, statusPane);
		mainPane.setOneTouchExpandable(true);
		mainPane.setDividerLocation(200);
		mainPane.setDividerSize(6);
		mainPane.setBorder(null);
		statusBar = createStatusBar();
		setLayout(new BorderLayout());
		add(mainPane, BorderLayout.CENTER);
		add(statusBar, BorderLayout.SOUTH);
		installToolBar();
	}

	public JFrame createFrame() {
		JFrame frame = new JFrame();
		frame.getContentPane().add(this);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setJMenuBar(new ControlMenuBar(this));
		frame.setSize(800, 600);
		updateTitle();
		return frame;
	}

	private void installToolBar() {
		add(new ControlToolBar(this, JToolBar.HORIZONTAL), BorderLayout.NORTH);
	}

	private JLabel createStatusBar() {
		JLabel statusBar = new JLabel("ready");
		statusBar.setBorder(BorderFactory.createEmptyBorder(2, 4, 2, 4));
		return statusBar;
	}

	/**
	 * Update the title of the window.
	 */
	public void updateTitle() {
		JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
		if (frame != null) {
			title = "Whack Moles Game";
			if (isRunning) {
				title += " - running";
			}
			frame.setTitle(title);
		}
	}

	/**
	 * Update the text of the status bar.
	 * 
	 * @param msg
	 */
	public void updateStatusBar(String msg) {
		statusBar.setText(msg);
	}

	public Action bind(String name, final Action action) {
		return bind(name, action, null);
	}

	public Action bind(String name, final Action action, Icon icon) {
		AbstractAction newAction = new AbstractAction(name, icon) {
			private static final long serialVersionUID = 6738094277262331128L;

			public void actionPerformed(ActionEvent e) {
				action.actionPerformed(new ActionEvent(mainPane, e.getID(), e.getActionCommand()));
			}
		};
		newAction.putValue(Action.SHORT_DESCRIPTION, action.getValue(Action.SHORT_DESCRIPTION));
		return newAction;
	}

	public void start() {
		isRunning = true;
		updateTitle();
		updateStatusBar("Running");
		if (source == null) {
			phoenixSource = BuildSource.makePhoenix(PrintStreamMessenger.err);
		} else {
			phoenixSource = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
		}
		MoteIF mif = new MoteIF(phoenixSource);
		game = new GameThread(this, mif);
		Thread thread = new Thread(game);
		thread.start();
	}

	public void chooseLevel(GameLevel level) {
		this.level = level;
	}

	public void stop() {
		if (phoenixSource != null) {
			phoenixSource.shutdown();
		}
		if (game != null) {
			Thread thread = new Thread(game);
			thread.interrupt();
			game = null;
			System.gc();
			// Thread.currentThread().interrupt();
		}
	}

	public void options() {
		JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
		if (frame != null) {
			OptionsDialog options = new OptionsDialog(this, frame);
			options.setModal(true);
			int x = frame.getX() + (frame.getWidth() - options.getWidth()) / 2;
			int y = frame.getY() + (frame.getHeight() - options.getHeight()) / 2;
			options.setLocation(x, y);
			options.setVisible(true);
		}
	}

	public void about() {
		JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
		if (frame != null) {
			AboutDialog about = new AboutDialog(frame);
			about.setModal(true);
			int x = frame.getX() + (frame.getWidth() - about.getWidth()) / 2;
			int y = frame.getY() + (frame.getHeight() - about.getHeight()) / 2;
			about.setLocation(x, y);
			about.setVisible(true);
		}
	}

	public void exit() {
		JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
		if (frame != null) {
			frame.dispose();
		}
	}

	public void setLevel(GameLevel level) {
		this.level = level;
	}

	public GameLevel getLevel() {
		return level;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSource() {
		return source;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public GameRankPane getRankPane() {
		return rankPane;
	}

	public GameStatusPane getStatusPane() {
		return statusPane;
	}

	public static void main(String... strings) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ServerUI server = new ServerUI();
		server.createFrame().setVisible(true);
		;
	}

}
