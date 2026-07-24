import java.util.LinkedList;
import java.util.Queue;

class LevelOrderIterator implements TreeIterator {
    private final Queue<TreeNode> queue = new LinkedList<>();

    LevelOrderIterator(TreeNode root) {
        if (root != null) queue.offer(root);
    }

    public boolean hasNext() {
        return !queue.isEmpty();
    }

    public int next() {
        TreeNode node = queue.poll();
        if (node.left  != null) queue.offer(node.left);
        if (node.right != null) queue.offer(node.right);
        return node.val;
    }
}
