package org.tuc.wmg.utils;

import java.util.ArrayList;
import java.util.List;

import org.tuc.wmg.GameMsg;
import org.tuc.wmg.ServerUI;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

public class CheckMolesUtil implements Runnable, MessageListener {

	private ServerUI server;

	private MoteIF moteIF;
	private PhoenixSource phoenixSource;

	private int countMoles = 0;
	private List<Integer> listMoles = new ArrayList<Integer>(0);

	public CheckMolesUtil(ServerUI server) {
		this.server = server;
		String source = server.getSource();
		if (source == null) {
			phoenixSource = BuildSource.makePhoenix(PrintStreamMessenger.err);
		} else {
			phoenixSource = BuildSource.makePhoenix(source, PrintStreamMessenger.err);
		}
		moteIF = new MoteIF(phoenixSource);
		moteIF.registerListener(new GameMsg(), this);
	}

	/**
	 * Send ping to check the current available moles.
	 */
	public void sendRequest() {
		countMoles = 0;
		listMoles.clear();
		GameMsg msg = new GameMsg();
		try {
			msg.set_type(0x01);
			msg.set_data(0x01);
			moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
			server.getStatusPane().appendInfo("Checking... Wait...");
			Thread thread = new Thread() {
				public void run() {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					server.getStatusPane().appendInfo("Current number of moles: " + countMoles);
					server.setNumMoles(countMoles);
					server.getLevel().setNumMoles(countMoles);
					server.getStatusPane().initGamepane();
				}
			};
			thread.start();
		} catch (Exception ioexc) {
		}
	}

	@Override
	public void messageReceived(int to, Message message) {
		GameMsg msg = (GameMsg) message;
		int source = message.getSerialPacket().get_header_src();
		int type = msg.get_type();
		// int data = msg.get_data();
		if (type == 0x11) { // ACK: ready
			if (!listMoles.contains(source)) {
				countMoles++;
				String text = "Mole." + source + " is available. ";
				server.getStatusPane().appendInfo(text);
				listMoles.add(source);
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			this.sendRequest();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stop() {
		if (phoenixSource != null) {
			phoenixSource.shutdown();
		}
	}

}
