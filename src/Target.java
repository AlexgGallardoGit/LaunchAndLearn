public class Target {
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

    public Target(double maxStructureHeight) {
        this.height = Math.random() * maxStructureHeight;
        this.width = this.height;
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

    public boolean contains(double x, double y) {
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }
}
