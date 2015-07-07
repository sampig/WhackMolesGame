package org.tuc.wmg;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GameStatusPane extends JPanel {

    private static final long serialVersionUID = 1935065644500091308L;

    private JLabel serialInfo;
    private JPanel gamePane;
    private JTextArea infoTextArea;

    public GameStatusPane(ServerUI server) {
        serialInfo = new JLabel();
        serialInfo.setText(server.getSource());
        gamePane = new JPanel();
        infoTextArea = new JTextArea();
        infoTextArea.setRows(10);
        setLayout(new BorderLayout());
        add(serialInfo, BorderLayout.NORTH);
        add(gamePane, BorderLayout.CENTER);
        add(infoTextArea, BorderLayout.SOUTH);
    }

    public void updateSerialInfo(String text) {
        serialInfo.setText(text);
    }

    public void appendInfo(String text) {
        infoTextArea.append(text + "\n");
    }

}
