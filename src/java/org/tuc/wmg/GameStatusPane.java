package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameStatusPane extends JPanel {

    private static final long serialVersionUID = 1935065644500091308L;

    private JLabel serialInfo;
    private JScrollPane serialPane, gamePane, infoPane;
    private JTextArea infoTextArea;

    public GameStatusPane(ServerUI server) {
        serialInfo = new JLabel();
        serialInfo.setText(server.getSource());
        serialPane = new JScrollPane(serialInfo);
        gamePane = new JScrollPane();
        infoTextArea = new JTextArea();
        infoTextArea.setRows(10);
        infoPane = new JScrollPane(infoTextArea);
        infoPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        infoPane.setPreferredSize(new Dimension(350, 300));
        infoPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Information:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), infoPane.getBorder()));
        setLayout(new BorderLayout());
        add(serialPane, BorderLayout.NORTH);
        add(gamePane, BorderLayout.CENTER);
        add(infoPane, BorderLayout.SOUTH);
    }

    public void updateSerialInfo(String text) {
        serialInfo.setText(text);
    }

    public void appendInfo(String text) {
        infoTextArea.append(text + "\n");
    }

}
