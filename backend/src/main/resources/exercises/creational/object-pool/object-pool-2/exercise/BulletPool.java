import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

// Provided — do not edit
class Bullet {
    private boolean active = false;
    private int x, y;

    void activate(int x, int y) { this.active = true; this.x = x; this.y = y; }
    void reset()                 { this.active = false; this.x = 0; this.y = 0; }

    public boolean isActive() { return active; }
    public int getX()         { return x; }
    public int getY()         { return y; }
}

class BulletPool {
    // TODO: add collections for available and active bullets

    BulletPool(int size) {
        // TODO: pre-fill the pool with 'size' Bullet objects
    }

    public Bullet fire(int x, int y) {
        // TODO: activate and return a bullet from the pool, or return null if exhausted
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void recycle(Bullet bullet) {
        // TODO: reset the bullet and return it to the available set
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int availableCount() {
        // TODO: return the number of currently available bullets
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int activeCount() {
        // TODO: return the number of currently active bullets
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
