package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GamePlayWindow extends JDialog {

	private static final long serialVersionUID = -802653897422611115L;
	
	private JPanel configPane, playPane, resultPane;
	private ServerUI server;

	private List<JLabel> listMoles = new ArrayList<>(0);
	
	public GamePlayWindow(ServerUI server, Frame owner) {
		super(owner);
		this.server = server;
		configPane = new JPanel();
		playPane = new JPanel();
		resultPane = new JPanel();
		add(configPane, BorderLayout.NORTH);
		add(playPane, BorderLayout.CENTER);
		add(resultPane, BorderLayout.SOUTH);
		setSize(400, 400);
	}

}
