/*
 * ControlMenuBar - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg.menu;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.tuc.wmg.ServerUI;
import org.tuc.wmg.menu.ControlActions.AboutAction;
import org.tuc.wmg.menu.ControlActions.CheckMolesAction;
import org.tuc.wmg.menu.ControlActions.ExitAction;
import org.tuc.wmg.menu.ControlActions.NewAction;
import org.tuc.wmg.menu.ControlActions.OptionAction;
import org.tuc.wmg.menu.ControlActions.StatisticAction;

/**
 * Menu bar for all controls.
 * 
 * @author Chenfeng ZHU
 * @see JMenuBar
 *
 */
public class ControlMenuBar extends JMenuBar {

	private static final long serialVersionUID = 1287447199249908420L;

	public ControlMenuBar(final ServerUI server) {
		JMenu menu = null;
		// JMenuItem item = null;

		// Create the Game menu
		menu = add(new JMenu("Game"));
		menu.add(server.bind("New Game", new NewAction()));
		menu.addSeparator();
		menu.add(server.bind("Statistic", new StatisticAction()));
		menu.add(server.bind("Option", new OptionAction()));
		menu.addSeparator();
		menu.add(server.bind("Exit", new ExitAction()));

		// Create the Tools menu
		menu = add(new JMenu("Tools"));
		menu.add(server.bind("Check Moles", new CheckMolesAction(CheckMolesAction.START)));
		menu.add(server.bind("Stop Checking", new CheckMolesAction(CheckMolesAction.STOP)));
		menu.addSeparator();

		// Create the Help menu
		menu = add(new JMenu("Help"));
		menu.add(server.bind("About", new AboutAction()));
	}

}
