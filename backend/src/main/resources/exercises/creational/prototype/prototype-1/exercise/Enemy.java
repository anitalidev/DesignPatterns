import java.util.ArrayList;
import java.util.List;

class Enemy {
    private String type;
    private int health;
    private int speed;
    private List<String> weapons;

    Enemy(String type, int health, int speed, List<String> weapons) {
        this.type    = type;
        this.health  = health;
        this.speed   = speed;
        this.weapons = new ArrayList<>(weapons);
    }

    // TODO: return a deep copy of this enemy
    public Enemy clone() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void addWeapon(String weapon) { weapons.add(weapon); }
    public void setHealth(int health)    { this.health = health; }

    public String       getType()    { return type; }
    public int          getHealth()  { return health; }
    public int          getSpeed()   { return speed; }
    public List<String> getWeapons() { return weapons; }
}

// Provided — do not edit
class EnemySpawner {
    private final Enemy prototype;

    EnemySpawner(Enemy prototype) {
        this.prototype = prototype;
    }

    public Enemy spawn() {
        return prototype.clone();
    }
}
