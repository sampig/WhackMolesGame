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
				server.start();
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

	public static class CheckMolesAction extends AbstractAction {

		private static final long serialVersionUID = 5862640899617176382L;

		@Override
		public void actionPerformed(ActionEvent e) {
			ServerUI server = getServer(e);
			if (server != null) {
				;// server.checkMoles();
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
				server.stop();
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
