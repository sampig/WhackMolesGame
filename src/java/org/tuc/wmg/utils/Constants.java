package org.tuc.wmg.utils;

/**
 * All constants stored here.
 * 
 * @author Chenfeng Zhu
 *
 */
public abstract class Constants {

	/**
	 * every X seconds a check SYN is sent.
	 */
	public final static int CHECK_FREQUENCY = 5000;

	/**
	 * the time waiting for the ACK.
	 */
	public final static int CHECK_WAIT_TIME = 1000;

	/**
	 * Define all types and datas in all kinds of messages.
	 *
	 */
	public abstract class Msg {

		/**
		 * SYN of check availability of moles.
		 */
		public final static int SYN_CHECK_TYPE = 0x01;
		/**
		 * type of ACK message for availability reply.
		 */
		public final static int ACK_AVAILABLE_TYPE = 0x11;
		/**
		 * data of ACK message for availability reply if it is a mole.
		 */
		public final static int ACK_AVAILABLE_MOLE = 0x10;
		/**
		 * data of ACK message for availability reply if it is the gun.
		 */
		public final static int ACK_AVAILABLE_GUN = 0x20;

		// Configuration
		/**
		 * type of message containing mole's timeout information.
		 */
		public final static int TYPE_TIMEOUT = 0x01;
		/**
		 * type of ACK message for ready.
		 */
		public final static int ACK_READY_TYPE = 0x11;
		/**
		 * data of ACK message for ready if it is a mole.
		 */
		public final static int ACK_READY_MOLE = 0x10;
		/**
		 * data of ACK message for ready if it is the gun.
		 */
		public final static int ACK_READY_GUN = 0x20;

		/**
		 * SYN for game start.
		 */
		public final static int SYN_GAME_START = 0x12;

		// Game starts.
		/**
		 * type of SYN message for sending mole ID.
		 */
		public final static int SYN_MOLE_ID = 0x21;
		/**
		 * ACK message for my turn reply.
		 */
		public final static int ACK_MYTURN = 0x13;
		/**
		 * type of message containing hitting status.
		 */
		public final static int TYPE_STATUS = 0x22;
		/**
		 * ACK message for status reply.
		 */
		public final static int ACK_STATUS = 0x14;
		/**
		 * type of message containing gun's one-time pressing duration.
		 */
		public final static int TYPE_PRESS_DUR = 0x23;
		/**
		 * type of ACK message for duration reply.
		 */
		public final static int ACK_PRESS_DUR = 0x15;

		// Game over.
		/**
		 * SYN for game over.
		 */
		public final static int SYN_GAME_OVER = 0xFF;

	}

}
