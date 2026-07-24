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
