package org.tuc.wmg;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;

public class GameThread implements Runnable, MessageListener {

    private ServerUI server;
    private GameLevel level = GameLevel.LEVEL_BEGINNER;
    private int totalTimes = 10;
    private double timeoutMole = 5;
    private double timeoutServer = 5;

    private MoteIF moteIF;

    public GameThread(ServerUI server, MoteIF moteIF) {
        this.server = server;
        level = server.getLevel();
        totalTimes = level.getTotalTimes();
        timeoutMole = level.getTimeoutMole();
        timeoutServer = level.getTimeoutServer();
        this.moteIF = moteIF;
        this.moteIF.registerListener(new GameMsg(), this);
    }

    @Override
    public void run() {
        // for (int i = 0; i < totalTimes; i++) {
        // try {
        // Thread.sleep((long) (timeoutMole * 1000));
        // Thread.sleep((long) (timeoutServer * 1000));
        // } catch (InterruptedException e) {
        // e.printStackTrace();
        // }
        // }
    }

    @Override
    public void messageReceived(int to, Message message) {
        GameMsg msg = (GameMsg) message;
        int source = message.getSerialPacket().get_header_src();
        String text = "Game Message received from Mole." + source + ": Type=" + msg.get_type()
                + ", Data=" + msg.get_data();
        server.getStatusPane().appendInfo(text);
        System.out.println(text);
    }

}
