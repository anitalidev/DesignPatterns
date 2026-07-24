class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("File.getName() returns its name", () ->
            assertEquals("readme.txt", new File("readme.txt", 100).getName(), "File name should match"));
        test("File.getSize() returns its size", () ->
            assertEquals(2048L, new File("photo.jpg", 2048).getSize(), "File size should match"));
        test("Folder.getName() returns its name", () ->
            assertEquals("documents", new Folder("documents").getName(), "Folder name should match"));
        test("empty Folder.getSize() is 0", () ->
            assertEquals(0L, new Folder("empty").getSize(), "Empty folder size should be 0"));
        test("Folder with one file returns that file's size", () -> {
            Folder f = new Folder("docs"); f.add(new File("a.txt", 500));
            assertEquals(500L, f.getSize(), "Folder size should equal single file's size");
        });
        test("Folder sums multiple direct children", () -> {
            Folder f = new Folder("docs");
            f.add(new File("a.txt", 100)); f.add(new File("b.txt", 200)); f.add(new File("c.txt", 300));
            assertEquals(600L, f.getSize(), "Folder size should be sum of all children");
        });
        test("nested folders are summed recursively", () -> {
            Folder root = new Folder("root"); Folder sub = new Folder("sub");
            sub.add(new File("deep.txt", 400)); root.add(new File("top.txt", 100)); root.add(sub);
            assertEquals(500L, root.getSize(), "Root size should include nested folder");
        });
        test("deeply nested tree is summed correctly", () -> {
            Folder a = new Folder("a"); Folder b = new Folder("b"); Folder c = new Folder("c");
            c.add(new File("leaf.bin", 1000)); b.add(c); a.add(b); a.add(new File("sibling.txt", 250));
            assertEquals(1250L, a.getSize(), "Three-level deep tree should sum correctly");
        });
        test("File and Folder are both usable as FileSystemItem", () -> {
            FileSystemItem file = new File("x.txt", 10); FileSystemItem folder = new Folder("y");
            assertTrue(file != null && folder != null, "both should be assignable to FileSystemItem");
        });
        test("adding same file to two folders counts independently", () -> {
            Folder f1 = new Folder("f1"); Folder f2 = new Folder("f2");
            f1.add(new File("a.txt", 100)); f2.add(new File("b.txt", 200));
            assertEquals(100L, f1.getSize(), "f1 should only count its own children");
            assertEquals(200L, f2.getSize(), "f2 should only count its own children");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
