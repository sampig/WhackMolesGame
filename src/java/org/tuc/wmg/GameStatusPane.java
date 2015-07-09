package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class GameStatusPane extends JPanel {

    private static final long serialVersionUID = 1935065644500091308L;

    private ServerUI server;
    private JLabel sourceInfo;
    private JPanel sourcePane, gamePane;
    private JScrollPane infoPane;
    private JTextArea infoTextArea;

    private List<JLabel> listMoles = new ArrayList<>(0);

    public GameStatusPane(ServerUI server) {
        this.server = server;
        sourceInfo = new JLabel();
        sourceInfo.setText(server.getSource());
        sourcePane = new JPanel();
        sourcePane.add(sourceInfo);
        initGamepane();
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
        add(sourcePane, BorderLayout.NORTH);
        add(gamePane, BorderLayout.CENTER);
        add(infoPane, BorderLayout.SOUTH);
    }

    /**
     * Update the source.
     * 
     * @param text
     */
    public void updateSourceInfo(String text) {
        sourceInfo.setText(text);
    }

    public void initGamepane() {
        gamePane = new JPanel();
        GridLayout layout = new GridLayout(0, 3);
        gamePane.setLayout(layout);
        int num = server.getLevel().getNumMoles();
        for (int i = 0; i < num; i++) {
            JLabel label = new JLabel("Mole." + i);
            listMoles.add(label);
            gamePane.add(label);
        }
    }

    public void freshGamepane(int num) {
        for (int i = 0; i < listMoles.size(); i++) {
            JLabel label = listMoles.get(i);
            if (i == num) {
                label.setText("*** Mole." + i + " ***");
            } else {
                label.setText("Mole." + i);
            }
        }
    }

    /**
     * Update the Information.
     * 
     * @param text
     */
    public void appendInfo(String text) {
        infoTextArea.append(text + "\n");
    }

}
