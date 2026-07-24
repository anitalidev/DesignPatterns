import java.util.ArrayDeque;
import java.util.Deque;

// TODO: implement pre-order (root → left → right) traversal
// Visits each node before its children — useful for copying or serializing a tree
//
// Use a Deque as a stack (LIFO — last in, first out):
//   Deque<TreeNode> stack = new ArrayDeque<>()  — create a stack
//   stack.push(node)                            — adds to the top
//   stack.pop()                                 — removes from the top
//   stack.isEmpty()                             — true if no nodes remain
// Start with root on the stack. Each time next() is called, pop a node and
// push its right child first, then left — so left is on top and visited first.
class PreOrderIterator implements TreeIterator {

    PreOrderIterator(TreeNode root) {
        // TODO
    }

    public boolean hasNext() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int next() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
