public class Wall {
    private double width;
    private double height;

    public Wall() {
        this.width = 100;  // Example default width
        this.height = 200; // Example default height
    }

    public Wall(double maxStructureHeight) {
        this.height = Math.random() * maxStructureHeight;
        this.width = this.height * 2;  // Example proportion
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public boolean contains(double x, double y) {
        return x >= 0 && x <= width && y >= 0 && y <= height;
    }
}
