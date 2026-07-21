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
        test("File.getName() returns its name", () -> {
            FileSystemItem f = new File("readme.txt", 100);
            assertEquals("readme.txt", f.getName(), "File name should match");
        });

        test("File.getSize() returns its size", () -> {
            FileSystemItem f = new File("photo.jpg", 2048);
            assertEquals(2048L, f.getSize(), "File size should match");
        });

        test("Folder.getName() returns its name", () -> {
            FileSystemItem folder = new Folder("documents");
            assertEquals("documents", folder.getName(), "Folder name should match");
        });

        test("empty Folder.getSize() is 0", () -> {
            Folder folder = new Folder("empty");
            assertEquals(0L, folder.getSize(), "Empty folder size should be 0");
        });

        test("Folder with one file returns that file's size", () -> {
            Folder folder = new Folder("docs");
            folder.add(new File("a.txt", 500));
            assertEquals(500L, folder.getSize(), "Folder size should equal the single file's size");
        });

        test("Folder sums multiple direct children", () -> {
            Folder folder = new Folder("docs");
            folder.add(new File("a.txt", 100));
            folder.add(new File("b.txt", 200));
            folder.add(new File("c.txt", 300));
            assertEquals(600L, folder.getSize(), "Folder size should be sum of all children");
        });

        test("nested folders are summed recursively", () -> {
            Folder root = new Folder("root");
            Folder sub  = new Folder("sub");
            sub.add(new File("deep.txt", 400));
            root.add(new File("top.txt", 100));
            root.add(sub);
            assertEquals(500L, root.getSize(), "Root size should include nested folder contents");
        });

        test("deeply nested tree is summed correctly", () -> {
            Folder a = new Folder("a");
            Folder b = new Folder("b");
            Folder c = new Folder("c");
            c.add(new File("leaf.bin", 1000));
            b.add(c);
            a.add(b);
            a.add(new File("sibling.txt", 250));
            assertEquals(1250L, a.getSize(), "Three-level deep tree size should be correct");
        });

        test("File and Folder are both usable as FileSystemItem", () -> {
            FileSystemItem file   = new File("x.txt", 10);
            FileSystemItem folder = new Folder("y");
            assertTrue(file   != null, "File should be assignable to FileSystemItem");
            assertTrue(folder != null, "Folder should be assignable to FileSystemItem");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
