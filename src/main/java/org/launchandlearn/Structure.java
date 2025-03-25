package org.launchandlearn;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Structure {
    // Data attributes
    protected double leftXLocation;
    protected double rightXLocation;
    protected double structureHeight;
    protected double gamePaneWidth;
    protected double gamePaneHeight;

    // Getters
    public double getLeftXLocation() {
        return leftXLocation;
    }
    public double getRightXLocation() {
        return rightXLocation;
    }
    public double getStructureHeight() {
        return structureHeight;
    }
    public double getGamePaneWidth() {
        return gamePaneWidth;
    }
    public double getGamePaneHeight() {
        return gamePaneHeight;
    }

    // Setter
    public void setGamePaneWidth(double gamePaneWidth) {
        this.gamePaneWidth = gamePaneWidth;
    }
    public void setGamePaneHeight(double gamePaneHeight) {
        this.gamePaneHeight = gamePaneHeight;
    }


    // Get different color rectangles (will be defined in the subclasses)
    public abstract Group getStructure(int paneHeight);

}
