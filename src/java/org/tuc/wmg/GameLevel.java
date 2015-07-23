package org.tuc.wmg;

import java.util.Random;

/**
 * Game's Level.
 * 
 * @author Chenfeng ZHU
 *
 */
public enum GameLevel {

	LEVEL_BEGINNER, LEVEL_MEDIATE, LEVEL_ADVANCED, LEVEL_CUSTOM;

	private int totalTimes = 10;
	private int missTimes = 5;
	private double timeoutMole = 5;
	private double timeoutServer = 5;
	private int numMoles = 0;

	private GameLevel() {
	}

	private GameLevel(int totalTimes, double timeoutMole, double timeoutServer) {
		this.totalTimes = totalTimes;
		this.timeoutMole = timeoutMole;
		this.timeoutServer = timeoutServer;
	}

	/**
	 * Get a random Level.
	 * 
	 * @return
	 */
	public static GameLevel getRandomInstance() {
		Random r = new Random();
		switch (r.nextInt(3)) {
		case 0:
			return LEVEL_BEGINNER;
		case 1:
			return LEVEL_MEDIATE;
		case 2:
			return LEVEL_ADVANCED;
		}
		return LEVEL_BEGINNER;
	}

	/**
	 * According to the level, Set the values.
	 */
	public void init() {
		switch (this) {
		case LEVEL_BEGINNER:
			totalTimes = 10;
			timeoutMole = 5;
			timeoutServer = 5;
			// numMoles = 2;
			break;
		case LEVEL_MEDIATE:
			totalTimes = 20;
			timeoutMole = 4;
			timeoutServer = 4;
			// numMoles = 3;
			break;
		case LEVEL_ADVANCED:
			totalTimes = 30;
			timeoutMole = 2;
			timeoutServer = 2;
			// numMoles = 4;
			break;
		case LEVEL_CUSTOM:
			break;
		default:
			break;
		}
	}

	/**
	 * Set the custom level.
	 * 
	 * @param totalTimes
	 * @param timeoutMole
	 * @param timeoutServer
	 * @param numMoles
	 * @return
	 */
	public GameLevel setCustom(int totalTimes, double timeoutMole, double timeoutServer, int numMoles) {
		this.totalTimes = totalTimes;
		this.timeoutMole = timeoutMole;
		this.timeoutServer = timeoutServer;
		this.numMoles = numMoles;
		return LEVEL_CUSTOM;
	}

	/**
	 * Get the total times.
	 * 
	 * @return
	 */
	public int getTotalTimes() {
		this.init();
		return totalTimes;
	}

	/**
	 * Get the miss times.
	 * 
	 * @return
	 */
	public int getMissTimes() {
		this.init();
		return missTimes;
	}

	/**
	 * Get the timeout for mole.
	 * 
	 * @return
	 */
	public double getTimeoutMole() {
		this.init();
		return timeoutMole;
	}

	/**
	 * Get the timeout for server.
	 * 
	 * @return
	 */
	public double getTimeoutServer() {
		this.init();
		return timeoutServer;
	}

	/**
	 * Get the number of moles.
	 * 
	 * @return
	 */
	public int getNumMoles() {
		if (numMoles == 0) {
			switch (this) {
			case LEVEL_BEGINNER:
				return 3;
			case LEVEL_MEDIATE:
				return 4;
			case LEVEL_ADVANCED:
				return 5;
			default:
				break;
			}
		}
		return numMoles;
	}

	public void setNumMoles(int numMoles) {
		this.numMoles = numMoles;
	}

	public String getDetail() {
		String str = "Total times: " + totalTimes + ", Timeout Server: " + timeoutServer + ", Timeout Mole: "
				+ timeoutMole + ", Number of Moles: " + numMoles;
		return str;
	}

}
