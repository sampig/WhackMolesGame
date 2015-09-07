/*
 * CheckMolesUtil - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tuc.wmg.GameMsg;
import org.tuc.wmg.ServerUI;
import org.tuc.wmg.utils.Constants.Msg;

import net.tinyos.message.Message;
import net.tinyos.message.MessageListener;
import net.tinyos.message.MoteIF;
import net.tinyos.packet.BuildSource;
import net.tinyos.packet.PhoenixSource;
import net.tinyos.util.PrintStreamMessenger;

/**
 * A utility for checking the available motes.
 * 
 * @author Chenfeng Zhu
 *
 */
public class CheckMolesUtil implements Runnable, MessageListener {

    private ServerUI server;

    private MoteIF moteIF;
    private PhoenixSource phoenixSource;

    private int countMoles = 0;
    private List<MoteInfo> listMoles = new ArrayList<MoteInfo>(0);

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
     * Send a SYN to check the current available moles.
     */
    public void sendCheckSYN() {
        countMoles = 0;
        listMoles.clear();
        GameMsg msg = new GameMsg();
        try {
            msg.set_type(Msg.SYN_CHECK_TYPE);
            msg.set_data(0x01);
            moteIF.send(MoteIF.TOS_BCAST_ADDR, msg);
            server.getStatusPane().appendInfo("Checking available moles... Wait...");
            // start a thread waiting for replies.
            Thread thread = new Thread() {
                public void run() {
                    try {
                        Thread.sleep(Constants.CHECK_WAIT_TIME);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Collections.sort(listMoles);
                    server.getStatusPane().appendInfo(
                            "Current number of moles: " + countMoles + listMoles.toString());
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
        MoteInfo mote = new MoteInfo(source);
        int type = msg.get_type();
        int data = msg.get_data();
        if (type == Msg.ACK_AVAILABLE_TYPE) { // ACK: available
            if (data == Msg.ACK_AVAILABLE_MOLE) { // Mole
                if (!listMoles.contains(source)) {
                    countMoles++;
                    String text = mote + " is available. ";
                    server.getStatusPane().appendInfo(text);
                    listMoles.add(mote);
                }
            } else if (data == Msg.ACK_AVAILABLE_GUN) { // Gun
                String text = "Gun is available. ";
                server.getStatusPane().appendInfo(text);
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // send the check SYN every X seconds.
            this.sendCheckSYN();
            try {
                Thread.sleep(Constants.CHECK_FREQUENCY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stop listening.
     */
    public void stop() {
        if (phoenixSource != null) {
            phoenixSource.shutdown();
        }
    }

    /**
     * Get the currently available moles.
     * 
     * @return the list of available moles
     */
    public List<MoteInfo> getListMoles() {
        return listMoles;
    }

}
