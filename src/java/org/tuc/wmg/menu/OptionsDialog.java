package org.tuc.wmg.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.tuc.wmg.GameLevel;
import org.tuc.wmg.ServerUI;

public class OptionsDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 7515353703362196237L;

    private GameLevel level = GameLevel.LEVEL_BEGINNER;
    private JTextField sourceTF, nameTF;
    private JTextField numTF, timeMTF, timeSTF;
    private JLabel levelLabel;
    private JPanel levelPane;
    private JPanel custPane;

    private final static String STR_BEGINNER = "Beginner";
    private final static String STR_MEDIATE = "Mediate";
    private final static String STR_ADVANCED = "Advanced";
    private final static String STR_CUSTOM = "Custom";

    public OptionsDialog(final ServerUI server, Frame owner) {
        super(owner);
        setTitle("Options");
        setLayout(new BorderLayout());

        // <!-- Main Panel-->
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        mainPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Connect source
        JPanel sourcePane = new JPanel();
        sourcePane.setLayout(new FlowLayout(FlowLayout.LEFT));
        sourcePane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Connection Source:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), sourcePane.getBorder()));
        sourceTF = new JTextField(server.getSource(), 30);
        sourcePane.add(new JLabel("Comm: "));
        sourcePane.add(sourceTF);
        mainPane.add(sourcePane);

        // Player name
        JPanel playerPane = new JPanel();
        playerPane.setLayout(new FlowLayout(FlowLayout.LEFT));
        playerPane.setBorder(BorderFactory.createCompoundBorder(BorderFactory
                .createCompoundBorder(BorderFactory.createTitledBorder("Player:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), playerPane.getBorder()));
        nameTF = new JTextField("Player", 30);
        playerPane.add(new JLabel("Name: "));
        playerPane.add(nameTF);
        mainPane.add(playerPane);

        // Game level
        levelPane = new JPanel();
        levelPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Game Level:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), levelPane.getBorder()));
        levelPane.setLayout(new BorderLayout());
        JPanel levelP = new JPanel();
        levelP.setLayout(new GridLayout(0, 1));
        JRadioButton beginButton = new JRadioButton(STR_BEGINNER);
        beginButton.setActionCommand(STR_BEGINNER);
        beginButton.addActionListener(this);
        beginButton.setSelected(true);
        levelLabel = new JLabel("<html><ul><li>Total Times: <b>" + level.getTotalTimes()
                + "</b></li> <li>Time moles: <b>" + level.getTimeoutMole()
                + "</b>s</li> <li>Time server: <b>" + level.getTimeoutServer()
                + "</b>s</li></ul></html>");
        JRadioButton midButton = new JRadioButton(STR_MEDIATE);
        midButton.setActionCommand(STR_MEDIATE);
        midButton.addActionListener(this);
        JRadioButton advButton = new JRadioButton(STR_ADVANCED);
        advButton.setActionCommand(STR_ADVANCED);
        advButton.addActionListener(this);
        JRadioButton custButton = new JRadioButton(STR_CUSTOM);
        custButton.setActionCommand(STR_CUSTOM);
        custButton.addActionListener(this);
        ButtonGroup group = new ButtonGroup();
        group.add(beginButton);
        group.add(midButton);
        group.add(advButton);
        group.add(custButton);
        levelP.add(beginButton);
        levelP.add(midButton);
        levelP.add(advButton);
        levelP.add(custButton);
        custPane = new JPanel();
        custPane.setLayout(new GridLayout(0, 2));
        custPane.add(new JLabel("Total Times:"));
        numTF = new JTextField("10", 10);
        custPane.add(numTF);
        custPane.add(new JLabel("Timeout Server:"));
        timeSTF = new JTextField("5", 10);
        custPane.add(timeSTF);
        custPane.add(new JLabel("Timeout Mole:"));
        timeMTF = new JTextField("5", 10);
        custPane.add(timeMTF);
        levelPane.add(levelP, BorderLayout.LINE_START);
        levelPane.add(levelLabel, BorderLayout.CENTER);
        mainPane.add(levelPane);

        getContentPane().add(mainPane, BorderLayout.CENTER);
        // <!-- Main Panel-->

        // <!--Button Panel-->
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(16, 8, 8, 8)));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String source = sourceTF.getText();
                server.setSource(source);
                server.getStatusPane().updateSourceInfo(source);
                server.setLevel(level);
                server.setPlayerName(nameTF.getText());
                server.getStatusPane().appendInfo("Change Game Level: " + level);
                if (level == GameLevel.LEVEL_CUSTOM) {
                    level.setCustom(Integer.parseInt(numTF.getText()),
                            Integer.parseInt(timeSTF.getText()),
                            Integer.parseInt(timeMTF.getText()), 9);
                    server.getStatusPane().appendInfo("\tDetails: " + level.getDetail());
                }
                server.getStatusPane().initGamepane();
                setVisible(false);
            }
        });
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPanel.add(applyButton);
        buttonPanel.add(closeButton);
        getRootPane().setDefaultButton(closeButton);
        // <!--Button Panel-->

        setResizable(false);
        setSize(400, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        levelPane.remove(levelLabel);
        if (e.getActionCommand() == STR_BEGINNER) {
            custPane.setVisible(false);
            level = GameLevel.LEVEL_BEGINNER;
            levelLabel = new JLabel("<html><ul><li>Total Times: <b>" + level.getTotalTimes()
                    + "</b></li> <li>Time moles: <b>" + level.getTimeoutMole()
                    + "</b>s</li> <li>Time server: <b>" + level.getTimeoutServer()
                    + "</b>s</li></ul></html>");
            levelLabel.setVisible(true);
            levelPane.add(levelLabel, BorderLayout.CENTER);
        } else if (e.getActionCommand() == STR_MEDIATE) {
            custPane.setVisible(false);
            level = GameLevel.LEVEL_MEDIATE;
            levelLabel = new JLabel("<html><ul><li>Total Times: <b>" + level.getTotalTimes()
                    + "</b></li> <li>Time moles: <b>" + level.getTimeoutMole()
                    + "</b>s</li> <li>Time server: <b>" + level.getTimeoutServer()
                    + "</b>s</li></ul></html>");
            levelLabel.setVisible(true);
            levelPane.add(levelLabel, BorderLayout.CENTER);
        } else if (e.getActionCommand() == STR_ADVANCED) {
            custPane.setVisible(false);
            level = GameLevel.LEVEL_ADVANCED;
            levelLabel = new JLabel("<html><ul><li>Total Times: <b>" + level.getTotalTimes()
                    + "</b></li> <li>Time moles: <b>" + level.getTimeoutMole()
                    + "</b>s</li> <li>Time server: <b>" + level.getTimeoutServer()
                    + "</b>s</li></ul></html>");
            levelLabel.setVisible(true);
            levelPane.add(levelLabel, BorderLayout.CENTER);
        } else if (e.getActionCommand() == STR_CUSTOM) {
            custPane.setVisible(true);
            level = GameLevel.LEVEL_CUSTOM;
            levelLabel.setVisible(false);
            levelPane.add(custPane, BorderLayout.CENTER);
        }
        levelPane.revalidate();
        levelPane.repaint();
    }
}
