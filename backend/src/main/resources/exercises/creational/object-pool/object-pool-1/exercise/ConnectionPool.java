import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

class ConnectionPool {
    private final int size;

    // TODO: add a collection to track available connections

    ConnectionPool(int size) {
        this.size = size;
        // TODO: pre-fill the pool with 'size' Connection objects
    }

    public Connection acquire() {
        // TODO: return an available connection, or throw if none are free
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void release(Connection connection) {
        // TODO: return the connection to the available set
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int availableCount() {
        // TODO: return the number of currently available connections
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
