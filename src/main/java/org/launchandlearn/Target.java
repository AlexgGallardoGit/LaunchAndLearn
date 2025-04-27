package org.launchandlearn;

import javafx.scene.Group;
import javafx.scene.paint.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class Target extends Structure {
    private boolean isHit;
    private double width;
    private double height;
    private String color;

    public Target() {
        this.width = 5;  // Example default width
        this.height = 5; // Example default height
        this.color = "Red"; // Example default color
        this.isHit = false;
    }

    public Target(double maxStructureHeight, double width, double leftXLocation) {
        this.height = (0.01 + (Math.random() * (1 - 0.01))) * maxStructureHeight;
        this.width = width;
        this.leftXLocation = leftXLocation;
        this.rightXLocation = leftXLocation + width;
        this.color = "Red";
        this.isHit = false;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean getIsHit() {
        return isHit;
    }

    public double getWidth() {
        return width;
    }

    public double getHeigth() {
        return height;
    }

    public boolean contains(double x, double y, double paneHeight, int ballRadius) {
        double targetTop = paneHeight - height;
        double targetBottom = paneHeight;
        double targetLeft = leftXLocation;
        double targetRight = rightXLocation;

        double ballLeft = x - ballRadius;
        double ballRight = x + ballRadius;
        double ballTop = y - ballRadius;
        double ballBottom = y + ballRadius;

        return (ballRight >= targetLeft &&
                ballLeft <= targetRight &&
                ballBottom >= targetTop &&
                ballTop <= targetBottom);
    }

    @Override
    public Group getStructure(int paneHeight) {
        Rectangle target = new Rectangle();
        target.setWidth(width);
        target.setHeight(height);
        target.setX(getLeftXLocation());
        target.setY(paneHeight - height);
        target.setFill(Color.WHITE);

        // Set the stroke width (for all sides)
        target.setStrokeWidth(1);

        // Simulate the 4 sides with a stroke (top uses a special color)
        if (isHit) {
            target.setStroke(Color.GREEN); // Hide the default stroke
        } else {
            target.setStroke(Paint.valueOf(color));
        }

        // Add the four sides manually
        double x = getLeftXLocation();
        double y = paneHeight - height;

        // Bottom border - Always black
        Line bottomLine = new Line(x, y + height, x + width, y + height);
        bottomLine.setStroke(Color.BLACK);

        // Left border - Always black
        Line leftLine = new Line(x, y, x, y + height);
        leftLine.setStroke(Color.BLACK);

        // Right border - Always black
        Line rightLine = new Line(x + width, y, x + width, y + height);
        rightLine.setStroke(Color.BLACK);

        // Return a group containing the rectangle and the custom borders
        return new Group(target, bottomLine, leftLine, rightLine);
    }

}