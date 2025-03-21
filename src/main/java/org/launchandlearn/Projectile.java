package org.launchandlearn;//import javafx.scene.shape.QuadCurve;

public class Projectile {
    // Data Attributes
    private static final double numberOfPixelsPerMeter = 120;
    private double mass;
    private double force;
    private double angleInRadians;
    private double angleInDegrees;
    private double verticalVelocity;
    private double horizontalVelocity;
    private double gravityConstant;
    private double startX;
    private double startY;
    private double[] currentLocation;

    // No-args Constructor
    public Projectile() {
        //set the default values
        this.mass = 1;
        this.force = 0;
        this.angleInDegrees = 0;
        this.gravityConstant = 9.8 * numberOfPixelsPerMeter;
        //convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(0);
        this.startX = 0;
        this.startY = 0;
        this.currentLocation = new double[2];
    }

    // Parameterized Constructor
    public Projectile(double mass, double force, double angleInDegrees) {
        this.mass = mass;
        this.force = force;
        this.angleInDegrees = angleInDegrees;
        //convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(angleInDegrees);

        // Set the default values
        this.gravityConstant = 9.8 * numberOfPixelsPerMeter;
        this.startX = 0;
        this.startY = 0;
        this.currentLocation = new double[2];
    }
    public Projectile(double mass, double force, double angleInDegrees, double gravityConstant) {
        this.mass = mass;
        this.force = force;
        this.angleInDegrees = angleInDegrees;
        // Multiply the constant by numberOfPixelsPerMeter to maintain scale
        this.gravityConstant = numberOfPixelsPerMeter * gravityConstant;
        // Convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(angleInDegrees);

        // Set Default Values
        this.startX = 0;
        this.startY = 0;
    }
    public Projectile(double mass, double force, double angleInDegrees, double gravityConstant, double startX, double startY) {
        this.mass = mass;
        this.force = force;
        this.angleInDegrees = angleInDegrees;

        // Multiply the gravity constant by the number of pixels per meter
        this.gravityConstant = gravityConstant * numberOfPixelsPerMeter;

        //convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(angleInDegrees);
        this.startX = startX;
        this.startY = startY;
        this.currentLocation = new double[]{startX, startY};
    }

    // Getters and Setters
    public double getMass() {
        return mass;
    }
    public void setMass(double mass) {
        this.mass = mass;
    }
    public double getForce() {
        return force;
    }
    public void setForce(double force) {
        this.force = force;
    }
    public double getAngleInRadians() {
        return angleInRadians;
    }
    public double getAngleInDegrees() {
        return angleInDegrees;
    }
    public void setAngleInDegrees(double angleInDegrees) {
        // Convert the angleInDegrees to radians
        this.angleInRadians = Math.toRadians(angleInDegrees);
        this.angleInDegrees = angleInDegrees;
    }
    public void setAngleInRadians(double angleInRadians) {
        // Convert the angleInRadians to degrees
        this.angleInDegrees = Math.toDegrees(angleInRadians);
        this.angleInRadians = angleInRadians;
    }

    // Physics Projectile Calculations

    //calculate  vertical acceleration
    public double getVerticalAcceleration() {
        return (-1) * gravityConstant;
    }

    // Calculate the velocity
    public double calculateVelocity() {
        // Calculate the velocity V = F/m
        return force / mass;
    }
    public double calculateHorizontalVelocity() {
        // Calculate the horizontal velocity based on Vx = velocity * cos(angleInRadians)
        double horizontalVelocity = calculateVelocity() * Math.cos(angleInRadians);

        // Update the variable and return the value
        this.horizontalVelocity = horizontalVelocity;
        return horizontalVelocity;
    }
    public double calculateVerticalVelocity(double seconds) {
        // Calculate the initial y velocity(Vy = V * angleInRadians)
        double initialVerticalVelocity = calculateVelocity() * Math.sin(angleInRadians);

        // Calculate the vertical velocity at the desired seconds
        double verticalVelocity = initialVerticalVelocity + getVerticalAcceleration() * seconds;

        this.verticalVelocity = verticalVelocity;
        return verticalVelocity;
    }

    // Calculate the positions

    // Calculate the horizontal position
    public double calculateHorizontalPosition(double seconds) {
        System.out.println("Horizontal Position: " + (startX + calculateHorizontalVelocity() * seconds));
        return startX + calculateHorizontalVelocity() * seconds;
    }

    // Calculate the vertical position
    public double calculateVerticalPosition(double seconds) {
        double verticalPosition = startY + calculateVelocity() * Math.sin(angleInRadians) * seconds + (0.5 * getVerticalAcceleration() * seconds * seconds);
        System.out.println("Vertical Position: " + verticalPosition);
        return verticalPosition;
    }

    // getter/setter for the position array
    // Calculates, returns, and sets the current position
    public double[] getCurrentLocation(double seconds) {
        double[] currentLocation = new double[2];
        currentLocation[0] = calculateHorizontalPosition(seconds);
        currentLocation[1] = calculateVerticalPosition(seconds);

        //set the value of the current location array
        this.currentLocation[0] = currentLocation[0];
        this.currentLocation[1] = currentLocation[1];

        // return a copy of the array for security
        return currentLocation;
    }

    // Calculates and sets the current position
    public void setCurrentLocation(double seconds) {
        double[] currentLocation = new double[2];
        currentLocation[0] = calculateHorizontalPosition(seconds);
        currentLocation[1] = calculateVerticalPosition(seconds);

        //set the value of the current location array
        this.currentLocation[0] = currentLocation[0];
        this.currentLocation[1] = currentLocation[1];
    }

    /*

    public QuadCurve getTrajectoryParabola(Wall[] walls, QuadCurve[] targets, double deltaTime) {
    // Initialize the trajectory points
    double currentX = startX;
    double currentY = startY;

    double currentVelocityX = calculateHorizontalVelocity();
    double currentVelocityY = calculateVelocity() * Math.sin(angleInRadians);

    // Loop through the trajectory steps
    for (double t = 0; t < 10; t += deltaTime) {
        // Update position based on velocity and gravity
        currentX += currentVelocityX * deltaTime;
        currentY += currentVelocityY * deltaTime;

        // Apply gravity to the vertical velocity
        currentVelocityY += -gravityConstant * deltaTime;

        // Check if the projectile collides with a wall
        for (Wall wall : walls) {
            if (wall.isCollision(currentX, currentY)) { // Assuming wall has a method to check collision
                System.out.println("Projectile hit a wall at position: (" + currentX + ", " + currentY + ")");
                return new QuadCurve(startX, startY, (startX + currentX) / 2, (startY + currentY) / 2, currentX, currentY);
            }
        }

        // Check if the projectile reaches a target
        for (QuadCurve target : targets) {
            if (target.getStartX() <= currentX && currentX <= target.getEndX() &&
                target.getStartY() <= currentY && currentY <= target.getEndY()) {
                System.out.println("Projectile reached the target at position: (" + currentX + ", " + currentY + ")");
                return new QuadCurve(startX, startY, (startX + currentX) / 2, (startY + currentY) / 2, currentX, currentY);
            }
        }
    }

    // If no collision or target hit, return a full trajectory parabola
    return new QuadCurve(
        startX, startY,
        startX + currentVelocityX, startY + currentVelocityY,
        startX + currentVelocityX * 2, startY + currentVelocityY * 2 - 0.5 * gravityConstant * Math.pow(2, 2)
    );
}
     */

}
