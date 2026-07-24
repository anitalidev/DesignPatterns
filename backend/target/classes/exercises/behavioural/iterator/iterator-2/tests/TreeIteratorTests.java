import java.util.ArrayList;
import java.util.List;

class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    //       1
    //      / \
    //     2   3
    //    / \
    //   4   5
    static TreeNode tree() {
        TreeNode root = new TreeNode(1);
        root.left        = new TreeNode(2);
        root.right       = new TreeNode(3);
        root.left.left   = new TreeNode(4);
        root.left.right  = new TreeNode(5);
        return root;
    }

    static List<Integer> collect(TreeIterator it) {
        List<Integer> result = new ArrayList<>();
        while (it.hasNext()) result.add(it.next());
        return result;
    }

    public static void main(String[] args) {

        // --- LevelOrderIterator ---
        test("level-order: empty tree has no next", () -> {
            assertTrue(!new LevelOrderIterator(null).hasNext(), "hasNext() should be false for empty tree");
        });
        test("level-order: single node", () -> {
            List<Integer> result = collect(new LevelOrderIterator(new TreeNode(42)));
            assertEquals(List.of(42), result, "single-node level-order");
        });
        test("level-order: visits nodes level by level", () -> {
            List<Integer> result = collect(new LevelOrderIterator(tree()));
            assertEquals(List.of(1, 2, 3, 4, 5), result, "level-order traversal");
        });
        test("level-order: right-only tree", () -> {
            TreeNode root = new TreeNode(1); root.right = new TreeNode(2); root.right.right = new TreeNode(3);
            assertEquals(List.of(1, 2, 3), collect(new LevelOrderIterator(root)), "right-only level-order");
        });
        test("level-order: hasNext() is idempotent", () -> {
            LevelOrderIterator it = new LevelOrderIterator(new TreeNode(1));
            assertTrue(it.hasNext(), "first call"); assertTrue(it.hasNext(), "second call");
            assertEquals(1, it.next(), "next() should still return 1");
        });

        // --- InOrderIterator ---
        test("in-order: empty tree has no next", () -> {
            assertTrue(!new InOrderIterator(null).hasNext(), "hasNext() should be false for empty tree");
        });
        test("in-order: single node", () -> {
            List<Integer> result = collect(new InOrderIterator(new TreeNode(42)));
            assertEquals(List.of(42), result, "single-node in-order");
        });
        test("in-order: left -> root -> right", () -> {
            List<Integer> result = collect(new InOrderIterator(tree()));
            assertEquals(List.of(4, 2, 5, 1, 3), result, "in-order traversal");
        });
        test("in-order: left-only tree visits in ascending order", () -> {
            TreeNode root = new TreeNode(3); root.left = new TreeNode(2); root.left.left = new TreeNode(1);
            assertEquals(List.of(1, 2, 3), collect(new InOrderIterator(root)), "left-only in-order");
        });
        test("in-order: hasNext() is idempotent", () -> {
            InOrderIterator it = new InOrderIterator(new TreeNode(1));
            assertTrue(it.hasNext(), "first call"); assertTrue(it.hasNext(), "second call");
            assertEquals(1, it.next(), "next() should still return 1");
        });

        // --- PreOrderIterator ---
        test("pre-order: empty tree has no next", () -> {
            assertTrue(!new PreOrderIterator(null).hasNext(), "hasNext() should be false for empty tree");
        });
        test("pre-order: single node", () -> {
            List<Integer> result = collect(new PreOrderIterator(new TreeNode(42)));
            assertEquals(List.of(42), result, "single-node pre-order");
        });
        test("pre-order: root -> left -> right", () -> {
            List<Integer> result = collect(new PreOrderIterator(tree()));
            assertEquals(List.of(1, 2, 4, 5, 3), result, "pre-order traversal");
        });
        test("pre-order: right-only tree", () -> {
            TreeNode root = new TreeNode(1); root.right = new TreeNode(2); root.right.right = new TreeNode(3);
            assertEquals(List.of(1, 2, 3), collect(new PreOrderIterator(root)), "right-only pre-order");
        });
        test("pre-order: hasNext() is idempotent", () -> {
            PreOrderIterator it = new PreOrderIterator(new TreeNode(1));
            assertTrue(it.hasNext(), "first call"); assertTrue(it.hasNext(), "second call");
            assertEquals(1, it.next(), "next() should still return 1");
        });

        // --- same interface, different strategies ---
        test("all three iterators implement TreeIterator and are interchangeable", () -> {
            TreeNode root = tree();
            TreeIterator level   = new LevelOrderIterator(root);
            TreeIterator inOrder = new InOrderIterator(root);
            TreeIterator pre     = new PreOrderIterator(root);
            // same collect() call works for all three — caller code does not change
            assertEquals(List.of(1, 2, 3, 4, 5), collect(level),   "level-order via TreeIterator");
            assertEquals(List.of(4, 2, 5, 1, 3), collect(inOrder), "in-order via TreeIterator");
            assertEquals(List.of(1, 2, 4, 5, 3), collect(pre),     "pre-order via TreeIterator");
        });
        test("two independent iterators on the same tree do not interfere", () -> {
            TreeNode root = tree();
            InOrderIterator it1 = new InOrderIterator(root);
            InOrderIterator it2 = new InOrderIterator(root);
            assertEquals(4, it1.next(), "it1 first");
            assertEquals(4, it2.next(), "it2 first — independent of it1");
            assertEquals(2, it1.next(), "it1 second");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
