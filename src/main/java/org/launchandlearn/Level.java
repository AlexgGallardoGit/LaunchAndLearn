package org.launchandlearn;

public class Level {
    // Data Attributes
    private int currentLevel;

    // Constructor
    public Level() {
        this.currentLevel = 1;
    }
    public Level(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    // Getters
    public int getCurrentLevel() {
        return currentLevel;
    }

    // Setters
    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    // Calculate the Number of Walls
    public int calculateNumberOfWalls(int level) {
        if(level % 2 == 0) {
            return level / 2;
        }
        else {
            return (level - 1)/ 2;
        }
    }
    public int calculateNumberOfWalls() {
        return calculateNumberOfWalls(currentLevel);
    }

    // Calculate the Number of Targets
    public int calculateNumberOfTargets(int level) {
        if(level % 2 == 0) {
            return level / 2;
        }
        else {
            return (level + 1)/ 2;
        }
    }
    public int calculateNumberOfTargets() {
        return calculateNumberOfTargets(currentLevel);
    }

    // Get Score
    public char getScore(int numberOfTrys, int level) {
        if(numberOfTrys <= calculateNumberOfTargets(level)) {
            return 'A';
        }
        else if(numberOfTrys <= calculateNumberOfTargets(level) * 2) {
            return 'B';
        }
        else if(numberOfTrys <= calculateNumberOfTargets(level) * 3) {
            return 'C';
        }
        else if (numberOfTrys <= calculateNumberOfTargets(level) * 6) {
            return 'D';
        }
        else if (numberOfTrys <= calculateNumberOfTargets(level) * 10) {
            return 'E';
        }
        else {
            return 'F';
        }
    }
    public char getScore(int numberOfTrys) {
        return getScore(numberOfTrys, currentLevel);
    }
}
