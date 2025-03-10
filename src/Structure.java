import java.util.Arrays;

public abstract class Structure {
    // Data attributes
    protected static double[][] occupiedXLocation;
    protected double leftXLocation;
    protected double rightXLocation;
    protected double structureHeight;
    protected static double maxStrcutureHeight;

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
    public double getMaxStrcutureHeight() {
        return maxStrcutureHeight;
    }
    public double[][] getOccupiedXLocation() {
        //create a copy of the array for security
        return Arrays.copyOf(occupiedXLocation, occupiedXLocation.length);
    }

    // Setter
    public void setMaxStrcutureHeight(double maxStrcutureHeight) {
        Structure.maxStrcutureHeight = maxStrcutureHeight;
    }
    public void setOccupiedXLocation(double[][] occupiedXLocation) {
        Structure.occupiedXLocation = Arrays.copyOf(occupiedXLocation, occupiedXLocation.length);;
    }

    // Get different color rectangles (will be defined in the subclasses)
    public abstract Structure getStructure();

}
