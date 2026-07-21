class TestRunner {
    static int passed = 0, failed = 0;

    static void test(String name, Runnable fn) {
        try {
            fn.run();
            System.out.println("PASS: " + name);
            passed++;
        } catch (Exception | AssertionError e) {
            System.out.println("FAIL: " + name + " | " + e.getMessage());
            failed++;
        }
    }

    static void assertEquals(Object expected, Object actual, String msg) {
        if (!expected.equals(actual)) throw new AssertionError(msg + " — expected: " + expected + ", got: " + actual);
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        test("getTreeType() returns a non-null TreeType", () -> {
            TreeFactory factory = new TreeFactory();
            assertTrue(factory.getTreeType("Oak", "green", "rough") != null, "should return a TreeType");
        });

        test("returned TreeType has correct species", () -> {
            TreeFactory factory = new TreeFactory();
            TreeType t = factory.getTreeType("Oak", "green", "rough");
            assertEquals("Oak", t.getSpecies(), "species should match");
        });

        test("same species returns the same TreeType instance", () -> {
            TreeFactory factory = new TreeFactory();
            TreeType a = factory.getTreeType("Oak", "green", "rough");
            TreeType b = factory.getTreeType("Oak", "green", "rough");
            assertTrue(a == b, "same species should return the identical TreeType instance");
        });

        test("different species return different TreeType instances", () -> {
            TreeFactory factory = new TreeFactory();
            TreeType oak  = factory.getTreeType("Oak",  "green",  "rough");
            TreeType pine = factory.getTreeType("Pine", "darkgreen", "smooth");
            assertTrue(oak != pine, "different species should return different TreeType instances");
        });

        test("typeCount() reflects the number of distinct species", () -> {
            TreeFactory factory = new TreeFactory();
            factory.getTreeType("Oak",  "green",    "rough");
            factory.getTreeType("Pine", "darkgreen", "smooth");
            factory.getTreeType("Birch","white",    "papery");
            assertEquals(3, factory.typeCount(), "typeCount() should be 3 after three distinct species");
        });

        test("repeated requests for the same species do not increase typeCount()", () -> {
            TreeFactory factory = new TreeFactory();
            factory.getTreeType("Oak", "green", "rough");
            factory.getTreeType("Oak", "green", "rough");
            factory.getTreeType("Oak", "green", "rough");
            assertEquals(1, factory.typeCount(), "typeCount() should remain 1 for repeated same species");
        });

        test("Tree holds its position and shares the TreeType", () -> {
            TreeFactory factory = new TreeFactory();
            TreeType type = factory.getTreeType("Oak", "green", "rough");
            Tree tree = new Tree(10, 20, type);
            assertEquals(10,   tree.getX(),    "x should be 10");
            assertEquals(20,   tree.getY(),    "y should be 20");
            assertTrue(tree.getType() == type, "tree should reference the shared TreeType");
        });

        test("many trees of the same species share one TreeType", () -> {
            TreeFactory factory = new TreeFactory();
            Tree[] forest = new Tree[100];
            for (int i = 0; i < 100; i++) {
                TreeType type = factory.getTreeType("Oak", "green", "rough");
                forest[i] = new Tree(i, i * 2, type);
            }
            assertEquals(1, factory.typeCount(), "100 oak trees should share exactly one TreeType");
            assertTrue(forest[0].getType() == forest[99].getType(), "first and last tree should share the same TreeType");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
