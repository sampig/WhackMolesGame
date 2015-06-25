/*
 * ServerUI - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 * ServerUI provides an interface to record the game.
 * 
 * @author Chenfeng Zhu
 *
 */
public class ServerUI extends JPanel {

    private static final long serialVersionUID = -6911094015780609780L;

    public ServerUI() {
        ;
    }

    public JFrame createFrame() {
        JFrame frame = new JFrame();
        frame.getContentPane().add(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        return frame;
    }

    public static void main(String... strings) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        ServerUI server = new ServerUI();
        server.createFrame().setVisible(true);
        ;
    }

}
