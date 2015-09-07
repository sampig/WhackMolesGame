/*
 * GameRankPane - This file is part of Whack-Mole-Game
 * Copyright (C) 2015 - Chenfeng ZHU
 */
package org.tuc.wmg;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 * The panel to show the rank of all the results.
 * 
 * @author Chenfeng Zhu
 *
 */
public class GameRankPane extends JScrollPane {

    private static final long serialVersionUID = -4048211434815208714L;

    private JList<Object> jlistRank;
    private List<Record> listRank = new ArrayList<Record>(0);

    public GameRankPane(ServerUI server) {
        jlistRank = new JList<Object>();
        setViewportView(jlistRank);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Rank:"),
                        BorderFactory.createEmptyBorder(1, 1, 1, 1)), this.getBorder()));
        init();
    }

    /**
     * Initialization.
     */
    public void init() {
        // listRank.add(new Record("Demo1", 0.5));
        // listRank.add(new Record("Demo2", 0.2));
        // listRank.add(new Record("Demo3", 0.7));
        Collections.sort(listRank, Collections.reverseOrder());
        jlistRank.setListData(listRank.toArray());
        jlistRank.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    int index = jlistRank.locationToIndex(event.getPoint());
                    popResultDialog(index);
                }
            }
        });
    }

    /**
     * Insert a new result into the rank list.
     * 
     * @param rank
     */
    public void insertNewResult(Record rank) {
        listRank.add(rank);
        Collections.sort(listRank, Collections.reverseOrder());
        jlistRank.setListData(listRank.toArray());
    }

    /**
     * Insert a new result into the rank list.
     * 
     * @param name
     *            player's name
     * @param result
     *            result
     * @param hitTimes
     *            hitting times
     * @param totalTimes
     *            total times
     * @param pressTime
     *            pressing time
     */
    public void insertNewResult(String name, double result, int hitTimes, int totalTimes, double pressTime) {
        Record rank = new Record(name, result, hitTimes, totalTimes, pressTime);
        this.insertNewResult(rank);
    }

    /**
     * Pop up a dialog for the detail result.
     * 
     * @param index
     */
    public void popResultDialog(int index) {
        JFrame frame = (JFrame) SwingUtilities.windowForComponent(this);
        if (frame != null && index > 0 && index < listRank.size()) {
            Record record = listRank.get(index);
            ResultDetailDialog dialog = new ResultDetailDialog(frame, record);
            dialog.setModal(true);
            int x = frame.getX() + (frame.getWidth() - dialog.getWidth()) / 2;
            int y = frame.getY() + (frame.getHeight() - dialog.getHeight()) / 2;
            dialog.setLocation(x, y);
            dialog.setVisible(true);
        }
    }

    /**
     * Record.
     * 
     * @author Chenfeng ZHU
     *
     */
    public class Record implements Comparable<Record> {
        private String name;
        private double result; // %
        private int hitTimes;
        private int totalTimes;
        private double pressTime; // ms
        private int id = 0;

        public Record() {
            name = "Noname" + id;
        }

        public Record(String name, double result) {
            super();
            this.name = name;
            this.result = result;
        }

        public Record(String name, double result, int hitTimes, int totalTimes, double pressTime) {
            super();
            this.name = name;
            this.result = result;
            this.hitTimes = hitTimes;
            this.totalTimes = totalTimes;
            this.pressTime = pressTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getResult() {
            return result;
        }

        public void setResult(double result) {
            this.result = result;
        }

        public int getHitTimes() {
            return hitTimes;
        }

        public void setHitTimes(int hitTimes) {
            this.hitTimes = hitTimes;
        }

        public int getTotalTimes() {
            return totalTimes;
        }

        public void setTotalTimes(int totalTimes) {
            this.totalTimes = totalTimes;
        }

        public double getPressTime() {
            return pressTime;
        }

        public void setPressTime(double pressTime) {
            this.pressTime = pressTime;
        }

        @Override
        public int compareTo(Record o) {
            // result: the more, the better.
            if (this.result - o.result > 0) {
                return 1;
            } else if (this.result - o.result < 0) {
                return -1;
            } else {
                // press time: the less, the better.
                if (this.pressTime < o.pressTime) {
                    return 1;
                } else if (this.pressTime > o.pressTime) {
                    return -1;
                } else {
                    // total times: the more, the better.
                    if (this.totalTimes > o.totalTimes) {
                        return 1;
                    } else if (this.totalTimes < o.totalTimes) {
                        return -1;
                    }
                    return 0;
                }
            }
        }

        public String toString() {
            return name + ": " + result;
        }
    }

}
