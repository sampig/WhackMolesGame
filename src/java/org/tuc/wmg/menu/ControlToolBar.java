/*
 * ControlToolBar - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg.menu;

import javax.swing.BorderFactory;
import javax.swing.JToolBar;

import org.tuc.wmg.ServerUI;
import org.tuc.wmg.menu.ControlActions.ClearConsoleAction;
import org.tuc.wmg.menu.ControlActions.ExitAction;
import org.tuc.wmg.menu.ControlActions.NewAction;
import org.tuc.wmg.menu.ControlActions.StopAction;

/**
 * Tool bar.
 * 
 * @author Chenfeng Zhu
 *
 */
public class ControlToolBar extends JToolBar {

	private static final long serialVersionUID = -3638892129759875575L;

	public ControlToolBar(final ServerUI server, int orientation) {
		super(orientation);
		setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3), getBorder()));
		setFloatable(false);
		add(server.bind("Start", new NewAction()));
		// add(server.bind("Check", new CheckMolesAction()));
		add(server.bind("Clear", new ClearConsoleAction()));
		add(server.bind("Stop", new StopAction()));
		addSeparator();
		add(server.bind("Exit", new ExitAction()));
	}

}
