package org.tuc.wmg;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.tuc.wmg.GameRankPane.Record;

public class ResultDetailDialog extends JDialog {

	private static final long serialVersionUID = -5162914916198855313L;

	public ResultDetailDialog(Frame owner, Record record) {
		super(owner);
		setTitle(record.getName() + "\'s result");
		setLayout(new BorderLayout());

		// Content Panel
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
		content.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		content.add(new JLabel("Result: " + record.getResult()));
		content.add(new JLabel("Hitting times: " + record.getHitTimes()));
		content.add(new JLabel("Total times: " + record.getTotalTimes()));
		content.add(new JLabel("Pressing time: " + record.getPressTime()));
		getContentPane().add(content, BorderLayout.CENTER);

		// Button Panel
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		buttonPanel.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.GRAY), BorderFactory.createEmptyBorder(16, 8, 8, 8)));
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
		setSize(200, 200);
	}

}
