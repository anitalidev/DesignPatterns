class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("getTreeType() returns a non-null TreeType", () ->
            assertTrue(new TreeFactory().getTreeType("Oak", "green", "rough") != null, "should return a TreeType"));
        test("returned TreeType has correct species", () ->
            assertEquals("Oak", new TreeFactory().getTreeType("Oak", "green", "rough").getSpecies(), "species should match"));
        test("same species returns the same TreeType instance", () -> {
            TreeFactory f = new TreeFactory();
            assertTrue(f.getTreeType("Oak","green","rough") == f.getTreeType("Oak","green","rough"),
                "same species should return identical instance");
        });
        test("different species return different TreeType instances", () -> {
            TreeFactory f = new TreeFactory();
            assertTrue(f.getTreeType("Oak","green","rough") != f.getTreeType("Pine","darkgreen","smooth"),
                "different species should return different instances");
        });
        test("typeCount() reflects number of distinct species", () -> {
            TreeFactory f = new TreeFactory();
            f.getTreeType("Oak","green","rough"); f.getTreeType("Pine","darkgreen","smooth"); f.getTreeType("Birch","white","papery");
            assertEquals(3, f.typeCount(), "typeCount() should be 3 after three distinct species");
        });
        test("repeated requests for the same species do not increase typeCount()", () -> {
            TreeFactory f = new TreeFactory();
            f.getTreeType("Oak","green","rough"); f.getTreeType("Oak","green","rough"); f.getTreeType("Oak","green","rough");
            assertEquals(1, f.typeCount(), "typeCount() should remain 1 for repeated same species");
        });
        test("Tree holds its position and shares the TreeType", () -> {
            TreeFactory f = new TreeFactory(); TreeType type = f.getTreeType("Oak","green","rough");
            Tree tree = new Tree(10, 20, type);
            assertEquals(10, tree.getX(), "x should be 10"); assertEquals(20, tree.getY(), "y should be 20");
            assertTrue(tree.getType() == type, "tree should reference the shared TreeType");
        });
        test("many trees of the same species share one TreeType", () -> {
            TreeFactory f = new TreeFactory(); Tree[] forest = new Tree[100];
            for (int i = 0; i < 100; i++) forest[i] = new Tree(i, i*2, f.getTreeType("Oak","green","rough"));
            assertEquals(1, f.typeCount(), "100 oak trees should share exactly one TreeType");
            assertTrue(forest[0].getType() == forest[99].getType(), "first and last tree should share TreeType");
        });
        test("three species → three types, each repeated many times", () -> {
            TreeFactory f = new TreeFactory();
            for (int i = 0; i < 50; i++) f.getTreeType("Oak","g","r");
            for (int i = 0; i < 50; i++) f.getTreeType("Pine","dg","s");
            for (int i = 0; i < 50; i++) f.getTreeType("Birch","w","p");
            assertEquals(3, f.typeCount(), "should still be exactly 3 types");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
