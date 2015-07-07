package org.tuc.wmg;

import java.util.Random;

public enum GameLevel {

    LEVEL_BEGINNER, LEVEL_MEDIATE, LEVEL_ADVANCED, LEVEL_CUSTOM;

    private int totalTimes = 10;
    private int missTimes = 5;
    private double timeoutMole = 5;
    private double timeoutServer = 5;

    private GameLevel() {
    }

    private GameLevel(int totalTimes, double timeoutMole, double timeoutServer) {
        this.totalTimes = totalTimes;
        this.timeoutMole = timeoutMole;
        this.timeoutServer = timeoutServer;
    }

    public static GameLevel getRandomInstance() {
        Random r = new Random();
        switch (r.nextInt(3)) {
        case 0:
            return LEVEL_BEGINNER;
        case 1:
            return LEVEL_MEDIATE;
        case 2:
            return LEVEL_ADVANCED;
        }
        return LEVEL_BEGINNER;
    }

    public void init() {
        switch (this) {
        case LEVEL_BEGINNER:
            totalTimes = 10;
            timeoutMole = 5;
            timeoutServer = 5;
            break;
        case LEVEL_MEDIATE:
            totalTimes = 20;
            timeoutMole = 4;
            timeoutServer = 4;
            break;
        case LEVEL_ADVANCED:
            totalTimes = 30;
            timeoutMole = 3;
            timeoutServer = 3;
            break;
        case LEVEL_CUSTOM:
            break;
        default:
            break;
        }
    }

    public GameLevel setCustom(int totalTimes, double timeoutMole, double timeoutServer) {
        this.totalTimes = totalTimes;
        this.timeoutMole = timeoutMole;
        this.timeoutServer = timeoutServer;
        return LEVEL_CUSTOM;
    }

    public int getTotalTimes() {
        this.init();
        return totalTimes;
    }

    public int getMissTimes() {
        this.init();
        return missTimes;
    }

    public double getTimeoutMole() {
        this.init();
        return timeoutMole;
    }

    public double getTimeoutServer() {
        this.init();
        return timeoutServer;
    }

}
