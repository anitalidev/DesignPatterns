class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        // TreeFactory — sharing
        test("same species+color+texture returns the same TreeType instance", () -> {
            TreeFactory f = new TreeFactory();
            assertTrue(f.getTreeType("Oak","green","rough") == f.getTreeType("Oak","green","rough"),
                "expected same instance, got different");
        });
        test("different species return different TreeType instances", () -> {
            TreeFactory f = new TreeFactory();
            assertTrue(f.getTreeType("Oak","green","rough") != f.getTreeType("Pine","green","rough"),
                "expected different instances, got the same");
        });
        test("same species but different color returns a different instance", () -> {
            TreeFactory f = new TreeFactory();
            assertTrue(f.getTreeType("Oak","green","rough") != f.getTreeType("Oak","brown","rough"),
                "expected different instances, got the same");
        });
        test("typeCount() tracks distinct combinations", () -> {
            TreeFactory f = new TreeFactory();
            f.getTreeType("Oak","green","rough");
            f.getTreeType("Pine","darkgreen","smooth");
            f.getTreeType("Birch","white","papery");
            assertEquals(3, f.typeCount(), "typeCount should be 3 after three distinct requests");
        });
        test("repeated requests do not inflate typeCount()", () -> {
            TreeFactory f = new TreeFactory();
            for (int i = 0; i < 50; i++) f.getTreeType("Oak","green","rough");
            assertEquals(1, f.typeCount(), "50 identical requests should produce exactly 1 cached type");
        });

        // Forest — uses the factory, demonstrates sharing
        test("plantTree() adds a tree to the forest", () -> {
            Forest forest = new Forest();
            forest.plantTree(1, 2, "Oak", "green", "rough");
            assertEquals(1, forest.getTrees().size(), "forest should contain 1 tree");
        });
        test("planted tree has correct position", () -> {
            Forest forest = new Forest();
            forest.plantTree(5, 10, "Oak", "green", "rough");
            Tree t = forest.getTrees().get(0);
            assertEquals(5, t.getX(), "x should be 5");
            assertEquals(10, t.getY(), "y should be 10");
        });
        test("planted tree references a non-null TreeType", () -> {
            Forest forest = new Forest();
            forest.plantTree(0, 0, "Oak", "green", "rough");
            assertTrue(forest.getTrees().get(0).getType() != null, "expected non-null TreeType, got: null");
        });
        test("two trees of the same species share one TreeType instance", () -> {
            Forest forest = new Forest();
            forest.plantTree(0, 0, "Oak", "green", "rough");
            forest.plantTree(5, 5, "Oak", "green", "rough");
            assertTrue(forest.getTrees().get(0).getType() == forest.getTrees().get(1).getType(),
                "expected same TreeType instance, got different");
        });
        test("1000 oak trees produce only 1 TreeType", () -> {
            Forest forest = new Forest();
            for (int i = 0; i < 1000; i++) forest.plantTree(i, i, "Oak", "green", "rough");
            assertEquals(1, forest.treeTypeCount(), "1000 oaks should share exactly one TreeType");
            assertEquals(1000, forest.getTrees().size(), "forest should still contain 1000 trees");
        });
        test("three species produce three TreeTypes regardless of tree count", () -> {
            Forest forest = new Forest();
            for (int i = 0; i < 100; i++) forest.plantTree(i, 0, "Oak",   "green",     "rough");
            for (int i = 0; i < 100; i++) forest.plantTree(i, 1, "Pine",  "darkgreen",  "smooth");
            for (int i = 0; i < 100; i++) forest.plantTree(i, 2, "Birch", "white",      "papery");
            assertEquals(3,   forest.treeTypeCount(),    "300 trees across 3 species → 3 TreeTypes");
            assertEquals(300, forest.getTrees().size(),  "all 300 trees should be in the forest");
        });
        test("trees of different species have different TreeType instances", () -> {
            Forest forest = new Forest();
            forest.plantTree(0, 0, "Oak",  "green", "rough");
            forest.plantTree(1, 0, "Pine", "green", "rough");
            assertTrue(forest.getTrees().get(0).getType() != forest.getTrees().get(1).getType(),
                "expected different TreeType instances, got the same");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
