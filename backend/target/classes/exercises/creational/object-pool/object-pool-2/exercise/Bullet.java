// Provided — do not edit
class Bullet {
    private boolean active = false;
    private int x, y;

    void activate(int x, int y) { this.active = true; this.x = x; this.y = y; }
    void reset() { this.active = false; this.x = 0; this.y = 0; }

    public boolean isActive() { return active; }
    public int getX() { return x; }
    public int getY() { return y; }
}
