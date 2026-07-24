import java.util.ArrayDeque;
import java.util.Deque;

// TODO: implement in-order (left → root → right) traversal
// For a binary search tree, this visits nodes in ascending sorted order
//
// Use a Deque as a stack (LIFO — last in, first out):
//   stack.push(node)  — adds to the top
//   stack.pop()       — removes from the top
// Push all left-spine nodes upfront. When next() is called, pop the top node,
// then push the left spine of its right subtree so the next smallest follows.
class InOrderIterator implements TreeIterator {

    InOrderIterator(TreeNode root) {
        // TODO
    }

    public boolean hasNext() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int next() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
