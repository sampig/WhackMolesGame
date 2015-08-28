/*
 * AboutDialog - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The Dialog for the about information.
 * 
 * @author Chenfeng ZHU
 * @see JDialog
 * 
 */
public class AboutDialog extends JDialog {

    private static final long serialVersionUID = -8014226727696180405L;

    public AboutDialog(Frame owner) {
        super(owner);
        setTitle("About Whack Moles Game");
        setLayout(new BorderLayout());

        // Title and subtitle Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(8, 8, 12, 8)));
        JLabel titleLabel = new JLabel("Whack Moles Game");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(4, 0, 0, 0));
        titleLabel.setOpaque(false);
        titlePanel.add(titleLabel, BorderLayout.NORTH);
        JLabel subtitleLabel = new JLabel(
                "<html>For more info<br/> https://github.com/sampig/WhackMolesGame</html>");
        subtitleLabel.setBorder(BorderFactory.createEmptyBorder(4, 18, 0, 0));
        subtitleLabel.setOpaque(false);
        titlePanel.add(subtitleLabel, BorderLayout.CENTER);
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        // Content Panel
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        content.add(new JLabel("Whack Moles Game - The Server UI of Whack Moles Game"));
        content.add(new JLabel(" "));
        content.add(new JLabel("Version 1.0"));
        content.add(new JLabel("Copyright (C) 2015 by TUC."));
        content.add(new JLabel("Developed by Chenfeng Zhu."));
        content.add(new JLabel("All rights reserved."));
        content.add(new JLabel(" "));
        getContentPane().add(content, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(16, 8, 8, 8)));
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        buttonPanel.add(closeButton);
        getRootPane().setDefaultButton(closeButton);

        setResizable(false);
        setSize(400, 400);
    }

}
