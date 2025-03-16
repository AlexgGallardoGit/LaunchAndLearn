package org.launchandlearn;

import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.LinkedList;

public abstract class Structure {
    // Data attributes
    protected static LinkedList<double[]> occupiedXLocation = new LinkedList<>();
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
    public LinkedList<double[]> getOccupiedXLocation() {
        //create a copy of the array for security
        return new LinkedList<>(occupiedXLocation);
    }
    public double getGamePaneWidth() {
        return gamePaneWidth;
    }
    public double getGamePaneHeight() {
        return gamePaneHeight;
    }

    // Setter
    public void setOccupiedXLocation(LinkedList<double[]> occupiedXLocation) {
        this.occupiedXLocation = new LinkedList<>(occupiedXLocation);
    }
    public void setGamePaneWidth(double gamePaneWidth) {
        this.gamePaneWidth = gamePaneWidth;
    }
    public void setGamePaneHeight(double gamePaneHeight) {
        this.gamePaneHeight = gamePaneHeight;
    }


    // Get different color rectangles (will be defined in the subclasses)
    public abstract Group getStructure(int paneHeight);

}
