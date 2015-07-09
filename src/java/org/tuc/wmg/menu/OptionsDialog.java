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
    private JTextField sourceTF;
    private JLabel beginLabel, midLabel, advLabel;
    private JPanel custPane;

    private final static String STR_BEGINNER = "Beginner";
    private final static String STR_MEDIATE = "Mediate";
    private final static String STR_ADVANCED = "Advanced";
    private final static String STR_CUSTOM = "Custom";

    public OptionsDialog(final ServerUI server, Frame owner) {
        super(owner);
        setTitle("Options");
        setLayout(new BorderLayout());

        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));
        mainPane.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        // Connect source
        JPanel sourcePane = new JPanel();
        sourcePane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Connection Source:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), sourcePane.getBorder()));
        sourceTF = new JTextField(server.getSource(), 50);
        sourcePane.add(sourceTF);
        mainPane.add(sourcePane);

        getContentPane().add(mainPane, BorderLayout.CENTER);

        // Game level
        JPanel levelPane = new JPanel();
        levelPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createTitledBorder("Game Level:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), levelPane.getBorder()));
        levelPane.setLayout(new GridLayout(0, 2));
        JRadioButton beginButton = new JRadioButton(STR_BEGINNER);
        beginButton.setActionCommand(STR_BEGINNER);
        beginButton.setSelected(true);
        beginLabel = new JLabel("Total Times: " + GameLevel.LEVEL_BEGINNER.getTotalTimes()
                + ", time moles: " + GameLevel.LEVEL_BEGINNER.getTimeoutMole()
                + ", time server: " + GameLevel.LEVEL_BEGINNER.getTimeoutServer());
        JRadioButton midButton = new JRadioButton(STR_MEDIATE);
        midButton.setActionCommand(STR_MEDIATE);
        midLabel = new JLabel("Total Times: " + GameLevel.LEVEL_MEDIATE.getTotalTimes());
        JRadioButton advButton = new JRadioButton(STR_ADVANCED);
        advButton.setActionCommand(STR_ADVANCED);
        advLabel = new JLabel("Total Times: " + GameLevel.LEVEL_ADVANCED.getTotalTimes());
        JRadioButton custButton = new JRadioButton(STR_CUSTOM);
        custButton.setActionCommand(STR_CUSTOM);
        ButtonGroup group = new ButtonGroup();
        group.add(beginButton);
        group.add(midButton);
        group.add(advButton);
        group.add(custButton);
        levelPane.add(beginButton);
        levelPane.add(beginLabel);
        levelPane.add(midButton);
        levelPane.add(midLabel);
        levelPane.add(advButton);
        levelPane.add(advLabel);
        levelPane.add(custButton);
        custPane = new JPanel();
        custPane.add(new JLabel("Total Times:"));
        custPane.add(new JTextField());
        levelPane.add(custPane);
        mainPane.add(levelPane);

        // Button
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
                server.getStatusPane().updateSerialInfo(source);
                server.setLevel(level);
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

        setResizable(false);
        setSize(400, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == STR_BEGINNER) {
            custPane.setVisible(false);
            level = GameLevel.LEVEL_BEGINNER;
        } else if (e.getActionCommand() == STR_MEDIATE) {
            custPane.setVisible(false);
            level = GameLevel.LEVEL_MEDIATE;
        } else if (e.getActionCommand() == STR_ADVANCED) {
            custPane.setVisible(false);
            level = GameLevel.LEVEL_ADVANCED;
        } else if (e.getActionCommand() == STR_CUSTOM) {
            custPane.setVisible(true);
            level = GameLevel.LEVEL_CUSTOM;
        }
    }
}
