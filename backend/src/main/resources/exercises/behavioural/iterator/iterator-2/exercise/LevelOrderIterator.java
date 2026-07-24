import java.util.LinkedList;
import java.util.Queue;

// TODO: implement level-order (breadth-first) traversal
// Visits nodes level by level, left to right: root, then its children, then their children, etc.
//
// Use a Queue — it processes nodes in the order they were added (FIFO):
//   Queue<TreeNode> queue = new LinkedList<>()  — create a queue
//   queue.offer(node)                           — adds to the back
//   queue.poll()                                — removes from the front
//   queue.isEmpty()                             — true if no nodes remain
// Start by offering root. Each time next() is called, poll the front node,
// then offer its left and right children (if present) so the next level follows.
class LevelOrderIterator implements TreeIterator {

    LevelOrderIterator(TreeNode root) {
        // TODO
    }

    public boolean hasNext() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int next() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
