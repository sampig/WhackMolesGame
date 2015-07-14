package org.tuc.wmg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class GameRankPane extends JScrollPane {

    private static final long serialVersionUID = -4048211434815208714L;

    private JList<Object> jlistRank;
    private List<Rank> listRank = new ArrayList<Rank>(0);

    public GameRankPane(ServerUI server) {
        jlistRank = new JList<Object>();
        setViewportView(jlistRank);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Rank:"),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)), this.getBorder()));
        init();
    }

    public void init() {
        listRank.add(new Rank("Demo1", Math.random()));
        listRank.add(new Rank("Demo2", Math.random()));
        listRank.add(new Rank("Demo3", Math.random()));
        Collections.sort(listRank,Collections.reverseOrder());
        jlistRank.setListData(listRank.toArray());
    }

    public void insertNewResult(Rank rank) {
        listRank.add(rank);
        Collections.sort(listRank,Collections.reverseOrder());
        jlistRank.setListData(listRank.toArray());
    }

    public void insertNewResult(String name, double result, int hitTimes, int totalTimes) {
        Rank rank = new Rank(name, result, hitTimes, totalTimes);
        this.insertNewResult(rank);
    }

    /**
     * Record.
     * 
     * @author Chenfeng ZHU
     *
     */
    public class Rank implements Comparable<Rank> {
        private String name;
        private double result;
        private int hitTimes;
        private int totalTimes;
        private int id = 0;

        public Rank() {
            name = "Noname" + id;
        }

        public Rank(String name, double result) {
            super();
            this.name = name;
            this.result = result;
        }

        public Rank(String name, double result, int hitTimes, int totalTimes) {
            super();
            this.name = name;
            this.result = result;
            this.hitTimes = hitTimes;
            this.totalTimes = totalTimes;
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

        @Override
        public int compareTo(Rank o) {
            if (this.result - o.result > 0) {
                return 1;
            } else if (this.result - o.result < 0) {
                return -1;
            } else {
                return 0;
            }
        }

        public String toString() {
            return name + ": " + result;
        }
    }

}
