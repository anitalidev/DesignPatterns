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

    public Enemy clone() {
        return new Enemy(type, health, speed, weapons);
    }

    public void addWeapon(String weapon) { weapons.add(weapon); }
    public void setHealth(int health)    { this.health = health; }

    public String       getType()    { return type; }
    public int          getHealth()  { return health; }
    public int          getSpeed()   { return speed; }
    public List<String> getWeapons() { return weapons; }
}

class EnemySpawner {
    private final Enemy prototype;

    EnemySpawner(Enemy prototype) {
        this.prototype = prototype;
    }

    public Enemy spawn() {
        return prototype.clone();
    }
}
