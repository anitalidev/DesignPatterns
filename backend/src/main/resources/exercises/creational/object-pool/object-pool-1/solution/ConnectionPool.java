import java.util.ArrayDeque;
import java.util.Queue;

class Connection {
    private final int id;
    Connection(int id) { this.id = id; }
    public int getId() { return id; }
}

class ConnectionPool {
    private final int size;
    private final Queue<Connection> available = new ArrayDeque<>();

    ConnectionPool(int size) {
        this.size = size;
        for (int i = 0; i < size; i++) {
            available.add(new Connection(i));
        }
    }

    public Connection acquire() {
        if (available.isEmpty()) throw new IllegalStateException("No connections available");
        return available.poll();
    }

    public void release(Connection connection) {
        available.add(connection);
    }

    public int availableCount() {
        return available.size();
    }
}
