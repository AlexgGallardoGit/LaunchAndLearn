public class Projectile {
    // Data Attributes
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
        this.mass = 1;
        this.force = 0;
        this.angleInDegrees = 0;
        this.gravityConstant = 9.8;
        //convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(0);
    }

    // Parameterized Constructor
    public Projectile(double mass, double force, double angleInDegrees) {
        this.mass = mass;
        this.force = force;
        this.angleInDegrees = angleInDegrees;
        this.gravityConstant = 9.8;
        //convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(angleInDegrees);
    }
    public Projectile(double mass, double force, double angleInDegrees, double gravityConstant) {
        this.mass = mass;
        this.force = force;
        this.angleInDegrees = angleInDegrees;
        this.gravityConstant = gravityConstant;
        //convert the angleInRadians to radians
        this.angleInRadians = Math.toRadians(angleInDegrees);
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
        return -gravityConstant;
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
        return startX + calculateHorizontalVelocity() * seconds;
    }

    // Calculate the vertical position
    public double calculateVerticalPosition(double seconds) {
        return startY +  calculateVelocity() * angleInDegrees * seconds - 1/2 * gravityConstant * Math.pow(seconds, 2);
    }

    // getter/setter for the position array
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



}
