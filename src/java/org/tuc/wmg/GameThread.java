package org.tuc.wmg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.tuc.wmg.utils.Constants.MoleStatus;
import org.tuc.wmg.utils.Constants.Msg;
import org.tuc.wmg.utils.MoteInfo;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;

/**
 * A thread for game running: config, start, end.
 * 
 * @author Chenfeng ZHU
 *
 */
public class GameThread implements Runnable, MessageListener {

	/**
	 * Game status.
	 */
	public enum GameStat {
		CONFIGURE, RUNNING, OVER
	}

	public enum TimerType {
		CONFIG, RUNNING
	}

	public final static int TIMEOUT_WAITING = 5;

	private ServerUI server;
	private GameLevel level = GameLevel.LEVEL_BEGINNER;
	private int totalTimes = 10;
	private double timeoutMole = 5;
	private double timeoutServer = 5;
	private int numMoles = 4;
	private List<MoteInfo> listMoles = new ArrayList<MoteInfo>(0);
	private boolean gunReady = true;

	private TimerThread timerWait;

	private GameStat stat = GameStat.CONFIGURE;

	private int currentTimes = 1;
	private int hitTimes = 0;
	private int totalPressedTime = 0;

	private MoteIF moteIF;

	public GameThread(ServerUI server, MoteIF moteIF) {
		this.server = server;
		level = server.getLevel();
		totalTimes = level.getTotalTimes();
		timeoutMole = level.getTimeoutMole();
		timeoutServer = level.getTimeoutServer();
		numMoles = server.getNumMoles();
		this.moteIF = moteIF;
		this.moteIF.registerListener(new GameMsg(), this);
		this.init();
	}

	public void init() {
		stat = GameStat.CONFIGURE;
		currentTimes = 1;
		hitTimes = 0;
	}

	@Override
	public void run() {
		if (stat == GameStat.CONFIGURE) {
			this.sendConfiguration();
		} else if (stat == GameStat.RUNNING) {
			;
		} else if (stat == GameStat.OVER) {
			;
		}
	}

	@Override
	public void messageReceived(int to, Message message) {
		if (stat == GameStat.OVER) {
			return;
		}
		if (!(message instanceof GameMsg)) {
			return;
		}
		GameMsg msg = (GameMsg) message;
		int source = message.getSerialPacket().get_header_src();
		MoteInfo mote = new MoteInfo(source);
		int type = msg.get_type();
		int data = msg.get_data();
		String text = "(Msg received from " + mote + ": Type=" + type + ", Data=" + data + ".) ";
		if (stat == GameStat.CONFIGURE && type == Msg.ACK_READY_TYPE) {
			// ACK: ready
			// listMoles.size() < numMoles
			if (data == Msg.ACK_READY_MOLE) {
				if (!listMoles.contains(mote)) {
					text = mote + " is ready. " + text;
					server.getStatusPane().appendInfo(text);
					listMoles.add(mote);
				}
			} else if (data == Msg.ACK_READY_GUN) {
				text = "Gun is available. " + text;
				server.getStatusPane().appendInfo(text);
				gunReady = true;
			}
			if (listMoles.size() >= numMoles && gunReady) {
				stat = GameStat.RUNNING;
				this.startGame();
			}
			return;
		} else if (type == Msg.ACK_MYTURN) {
			// ACK: myTurn
			server.getStatusPane().appendInfo("Mole." + source + " came out. " + text);
			currentTimes++;
			return;
		} else if (type == Msg.TYPE_STATUS && stat == GameStat.RUNNING) {
			// Result: 0-miss;1-hit
			this.sendStatReply(source);
			boolean stat = (data == 0x01);
			text = "Mole." + source + " stat: " + (stat ? "hit" : "miss") + ". " + text;
			if (stat) {
				hitTimes++;
			}
			server.getStatusPane().appendInfo(text);
			server.getStatusPane().refreshGamePane(mote.getMid(), (stat ? MoleStatus.HIT : MoleStatus.MISSING));
			server.getPlayWindow().updateInfoLabel();
			server.getPlayWindow().refreshPlayPanel(mote.getMid(), (stat ? MoleStatus.HIT : MoleStatus.MISSING));
			this.sendMoleID();
		} else if (type == Msg.TYPE_PRESS_DUR && stat == GameStat.RUNNING) {
			// press time.
			totalPressedTime += data;
			text = "This pressed time: " + data + ", total: " + totalPressedTime;
			server.getStatusPane().appendInfo(text);
		}
		if (currentTimes > totalTimes) {
			this.sendGameOver();
			return;
		}
	}

	public void sendConfiguration() {
		listMoles.clear();
		GameMsg msg = new GameMsg();
		try {
			msg.set_type(Msg.TYPE_TIMEOUT);
			msg.set_data((int) timeoutMole);
			moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
			server.getStatusPane().appendInfo("Configurating... Wait...");
			server.getStatusPane().appendInfo("Game Level: " + level);
			timerWait = new TimerThread(TimerType.CONFIG);
			timerWait.start();
		} catch (Exception ioexc) {
		}
	}

	public void startGame() {
		Collections.sort(listMoles);
		GameMsg msg = new GameMsg();
		try {
			msg.set_type(Msg.SYN_GAME_START);
			msg.set_data(0x00);
			moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
			server.getStatusPane().appendInfo("Game starts.");
			server.getStatusPane().appendInfo(level.getDetail());
		} catch (Exception ioexc) {
		}
		this.sendMoleID();
	}

	/**
	 * Choose one of the moles.
	 */
	public void sendMoleID() {
		try {
			Thread.sleep((long) (timeoutServer * 1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		server.getStatusPane().refreshGamePane(-1, MoleStatus.OUT);
		GameMsg msg = new GameMsg();
		Random r = new Random(System.currentTimeMillis());
		int id = r.nextInt(numMoles);
		try {
			msg.set_type(Msg.SYN_MOLE_ID);
			msg.set_data(listMoles.get(id).getMid());
			moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
			server.getStatusPane().appendInfo("It is the turn of " + listMoles.get(id));
			server.getStatusPane().refreshGamePane(listMoles.get(id).getMid(), MoleStatus.OUT);
			server.getPlayWindow().updateInfoLabel();
			server.getPlayWindow().refreshPlayPanel(listMoles.get(id).getMid(), MoleStatus.OUT);
		} catch (Exception ioexc) {
		}
	}

	/**
	 * After receiving information of the status, reply with this.
	 * 
	 * @param source
	 *            moteID
	 */
	public void sendStatReply(int source) {
		GameMsg msg = new GameMsg();
		try {
			msg.set_type(Msg.ACK_STATUS);
			msg.set_data(source);
			moteIF.send(source, msg);
		} catch (Exception ioexc) {
		}
	}

	/**
	 * Game over. Print the results on the
	 */
	public void sendGameOver() {
		GameMsg msg = new GameMsg();
		double result = (hitTimes * 1.0 / totalTimes);
		StringBuffer text = new StringBuffer();
		text.append("*********************\n");
		text.append("***** Game Over *****\n");
		text.append(server.getPlayerName() + "\'s result: \n");
		text.append("Game Level: " + server.getLevel() + ".\n");
		text.append("Total Times: " + totalTimes + "\n");
		text.append("Hit Times: " + hitTimes + "\n");
		text.append("Result: " + result + "\n");
		text.append("*********************\n\n\n");
		try {
			msg.set_type(Msg.SYN_GAME_OVER);
			msg.set_data(0x00);
			moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
			server.getStatusPane().appendInfo(text.toString());
		} catch (Exception ioexc) {
		}
		server.getRankPane().insertNewResult(server.getPlayerName(), result, hitTimes, totalTimes);
		stat = GameStat.OVER;
		server.gameStop();
	}

	public int getTotalTimes() {
		return totalTimes;
	}

	public int getCurrentTimes() {
		return currentTimes;
	}

	public int getHitTimes() {
		return hitTimes;
	}

	public int getTotalPressedTime() {
		return totalPressedTime;
	}

	public List<MoteInfo> getListMoles() {
		return listMoles;
	}

	public class TimerThread extends Thread {

		private TimerType type = TimerType.CONFIG;

		public TimerThread(TimerType type) {
			this.type = type;
		}

		public TimerType getType() {
			return type;
		}

		public void run() {
			if (type == TimerType.CONFIG) {
				try {
					Thread.sleep((long) (TIMEOUT_WAITING * 1000));
					if (stat == GameStat.CONFIGURE) {
						server.getStatusPane().appendInfo("No repsponse. Send config again.");
						sendConfiguration();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
