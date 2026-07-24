import java.util.ArrayDeque;
import java.util.Deque;

class PreOrderIterator implements TreeIterator {
    private final Deque<TreeNode> stack = new ArrayDeque<>();

    PreOrderIterator(TreeNode root) {
        if (root != null) stack.push(root);
    }

    public boolean hasNext() {
        return !stack.isEmpty();
    }

    public int next() {
        TreeNode node = stack.pop();
        // push right first so left is on top and visited first
        if (node.right != null) stack.push(node.right);
        if (node.left  != null) stack.push(node.left);
        return node.val;
    }
}
