package org.tuc.wmg;

import java.util.HashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class GameRankPane extends JScrollPane {

    private static final long serialVersionUID = -4048211434815208714L;

    private JList<Object> listRank;
    private Set<Rank> setRank = new HashSet<Rank>(0);

    public GameRankPane(ServerUI server) {
        listRank = new JList<Object>();
        setViewportView(listRank);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        setBorder(BorderFactory.createCompoundBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Rank:"),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)), this.getBorder()));
        init();
    }

    public void init() {
        setRank.add(new Rank("Test1", Math.random()));
        setRank.add(new Rank("Test2", Math.random()));
        setRank.add(new Rank("Test3", Math.random()));
        listRank.setListData(setRank.toArray());
    }

    public void insertNewResult(String name, double result) {
        Rank rank = new Rank(name, result);
        setRank.add(rank);
        listRank.setListData(setRank.toArray());
    }

    public class Rank implements Comparable<Rank> {
        private String name;
        private double result;
        private int id = 0;

        public Rank() {
            name = "Noname" + id;
        }

        public Rank(String name, double result) {
            this.name = name;
            this.result = result;
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
