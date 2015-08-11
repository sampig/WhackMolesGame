/*
 * ControlActions - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.tuc.wmg.ServerUI;

/**
 * All actions.
 * 
 * @author Chenfeng ZHU
 * @see AbstractAction
 *
 */
public class ControlActions {

	/**
	 * Get the ServerUI from the event.
	 * 
	 * @param e
	 * @return
	 */
	public static final ServerUI getServer(ActionEvent e) {
		if (e.getSource() instanceof Component) {
			Component component = (Component) e.getSource();
			while (component != null && !(component instanceof ServerUI)) {
				component = component.getParent();
			}
			return (ServerUI) component;
		}
		return null;
	}

	/**
	 * Action for New Game.
	 */
	public static class NewAction extends AbstractAction {

		private static final long serialVersionUID = -3870939467454593577L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				server.gameStart();
			}
		}

	}

	/**
	 * Action for Statistic.
	 */
	public static class StatisticAction extends AbstractAction {

		private static final long serialVersionUID = -4478923132669436195L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				;
			}
		}
	}

	/**
	 * Action for Option.
	 */
	public static class OptionAction extends AbstractAction {

		private static final long serialVersionUID = -2032016697071352814L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				server.options();
			}
		}
	}

	/**
	 * Action for start/stop checking moles.
	 */
	public static class CheckMolesAction extends AbstractAction {

		private static final long serialVersionUID = 5862640899617176382L;
		private int operation;

		public final static int START = 0;
		public final static int STOP = 1;

		public CheckMolesAction(int operation) {
			super();
			this.operation = operation;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				switch (operation) {
				case START:
					server.startCheckMoles();
					break;
				case STOP:
					server.stopCheckMoles();
					break;
				}
			}
		}

	}

	/**
	 * Action for clear Console.
	 */
	public static class ClearConsoleAction extends AbstractAction {

		private static final long serialVersionUID = -7260483396813931610L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				server.getStatusPane().clearConsole();
			}
		}

	}

	/**
	 * Action for Stopping Game.
	 */
	public static class StopAction extends AbstractAction {

		private static final long serialVersionUID = -6312794077163121939L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				server.gameStop();
			}
		}

	}

	/**
	 * Action for Exit.
	 */
	public static class ExitAction extends AbstractAction {

		private static final long serialVersionUID = -7482724455307774411L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				server.exit();
			}
		}
	}

	/**
	 * Action for About information.
	 */
	public static class AboutAction extends AbstractAction {

		private static final long serialVersionUID = -7613037902056397587L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				server.about();
			}
		}
	}
}
