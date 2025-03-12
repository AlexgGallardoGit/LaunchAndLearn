import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Wall extends Structure {
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

    @Override
    public Rectangle getStructure(int paneWidth, int paneHeight) {
        Rectangle wall = new Rectangle();
        wall.setWidth(width);
        wall.setHeight(height);
        wall.setX(getLeftXLocation());
        wall.setY(paneHeight - height);
        wall.setStroke(Color.BLACK);
        wall.setFill(Color.WHITE);
        return wall;
    }
}
