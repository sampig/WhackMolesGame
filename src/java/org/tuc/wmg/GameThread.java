package org.tuc.wmg;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;

public class GameThread implements Runnable, MessageListener {

    public enum GameStat {
        CONFIGURE, RUNNING, OVER
    }

    public enum TimerType {
        CONFIG, RUNNING
    }

    public final static int TIMEOUT_WAITING = 2;

    private ServerUI server;
    private GameLevel level = GameLevel.LEVEL_BEGINNER;
    private int totalTimes = 10;
    private double timeoutMole = 5;
    private double timeoutServer = 5;
    private int numMoles = 4;
    private List<Integer> listMoles = new ArrayList<Integer>(0);

    private TimerThread timerWait;

    private GameStat stat = GameStat.CONFIGURE;

    private int currentTimes = 1;
    private int hitTimes = 0;

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
        GameMsg msg = (GameMsg) message;
        int source = message.getSerialPacket().get_header_src();
        int type = msg.get_type();
        int data = msg.get_data();
        String text = "Game Message received from Mole." + source + ": Type=" + type
                + ", Data=" + data + ". ";
        if (type == 0x11 && listMoles.size() < numMoles) { // ACK: ready
            if (!listMoles.contains(source)) {
                text += "Mole." + source + " is ready.";
                server.getStatusPane().appendInfo(text);
                listMoles.add(source);
            }
            if (listMoles.size() >= numMoles) {
                stat = GameStat.RUNNING;
                server.getStatusPane().appendInfo("Game starts.");
                if (timerWait != null && timerWait.isAlive()) {
                    timerWait.interrupt();
                }
                this.sendMoleID();
            }
            return;
        } else if (type == 0x13) { // ACK: myTurn
            server.getStatusPane().appendInfo("Mole." + source + " came out.");
            return;
        } else if (type == 0x22) { // Result: 0-miss;1-hit
            boolean stat = (data == 0x01);
            text += "Mole." + source + " stat: " + (stat ? "hit" : "miss") + ".";
            server.getStatusPane().appendInfo(text);
            if (stat) {
                hitTimes++;
            }
        }
        // server.getStatusPane().appendInfo(text);
        if (type == 0x22 && currentTimes <= totalTimes) {
            this.sendMoleID();
            currentTimes++;
        } else {
            this.sendGameOver();
            return;
        }
    }

    public void sendConfiguration() {
        GameMsg msg = new GameMsg();
        try {
            msg.set_type(0x01);
            msg.set_data((int) timeoutMole);
            moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
            server.getStatusPane().appendInfo("Configurating... Wait...");
            server.getStatusPane().appendInfo("Game Level: " + level);
            timerWait = new TimerThread(TimerType.CONFIG);
            timerWait.start();
        } catch (Exception ioexc) {
        }
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
        GameMsg msg = new GameMsg();
        Random r = new Random(System.currentTimeMillis());
        int id = r.nextInt(numMoles);
        try {
            msg.set_type(0x21);
            msg.set_data(listMoles.get(id));
            moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
            server.getStatusPane().appendInfo("It is the turn of Mole." + listMoles.get(id));
            server.getStatusPane().freshGamepane(id);
        } catch (Exception ioexc) {
        }
    }

    public void sendGameOver() {
        GameMsg msg = new GameMsg();
        StringBuffer text = new StringBuffer();
        text.append("**********\n");
        text.append("Game Over.\n");
        text.append("Game Level: " + server.getLevel() + ".\n");
        text.append("Total Times: " + totalTimes + "\n");
        text.append("Hit Times: " + hitTimes + "\n");
        text.append("Result: " + (hitTimes * 1.0 / totalTimes) + "\n");
        try {
            msg.set_type(0xFF);
            msg.set_data(0x00);
            moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
            server.getStatusPane().appendInfo(text.toString());
        } catch (Exception ioexc) {
        }
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
                    sendConfiguration();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
