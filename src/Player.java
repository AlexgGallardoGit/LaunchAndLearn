public class Player {
    // Data Attributes
    private Projectile projectile;

    // No-args Constructor
    public Player() {
        this.projectile = new Projectile();
    }
    // Parameterized Constructor
    public Player(Projectile projectile) {
        this.projectile = projectile;
    }

    // Setters and Getters
    public void setProjectile(Projectile projectile) {
        this.projectile = projectile;
    }
    public Projectile getProjectile() {
        return projectile;
    }
}
