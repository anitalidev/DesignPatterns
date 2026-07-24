import java.util.ArrayDeque;
import java.util.Deque;

class InOrderIterator implements TreeIterator {
    private final Deque<TreeNode> stack = new ArrayDeque<>();

    InOrderIterator(TreeNode root) {
        pushLeft(root);
    }

    private void pushLeft(TreeNode node) {
        while (node != null) {
            stack.push(node);
            node = node.left;
        }
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        TreeNode node = stack.pop();
        pushLeft(node.right);
        return node.val;
    }
}
