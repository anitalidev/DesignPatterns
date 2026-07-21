import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

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
    private final Queue<Bullet> available = new ArrayDeque<>();
    private final Set<Bullet>   active    = new HashSet<>();

    BulletPool(int size) {
        for (int i = 0; i < size; i++) {
            available.add(new Bullet());
        }
    }

    public Bullet fire(int x, int y) {
        if (available.isEmpty()) return null;
        Bullet bullet = available.poll();
        bullet.activate(x, y);
        active.add(bullet);
        return bullet;
    }

    public void recycle(Bullet bullet) {
        if (active.remove(bullet)) {
            bullet.reset();
            available.add(bullet);
        }
    }

    public int availableCount() { return available.size(); }
    public int activeCount()    { return active.size(); }
}
