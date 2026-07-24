class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("hasNext() is false on empty shelf", () -> {
            assertTrue(!new BookShelf(5).iterator().hasNext(), "empty shelf should have no next");
        });
        test("hasNext() is true after adding a book", () -> {
            BookShelf shelf = new BookShelf(5); shelf.add(new Book("Dune"));
            assertTrue(shelf.iterator().hasNext(), "shelf with one book should have next");
        });
        test("next() returns the first book added", () -> {
            BookShelf shelf = new BookShelf(5); shelf.add(new Book("Dune"));
            assertEquals("Dune", shelf.iterator().next().getTitle(), "first book should be Dune");
        });
        test("books are returned in insertion order", () -> {
            BookShelf shelf = new BookShelf(5);
            shelf.add(new Book("A")); shelf.add(new Book("B")); shelf.add(new Book("C"));
            BookIterator it = shelf.iterator();
            assertEquals("A", it.next().getTitle(), "first");
            assertEquals("B", it.next().getTitle(), "second");
            assertEquals("C", it.next().getTitle(), "third");
        });
        test("hasNext() becomes false after all books are consumed", () -> {
            BookShelf shelf = new BookShelf(5);
            shelf.add(new Book("X")); shelf.add(new Book("Y"));
            BookIterator it = shelf.iterator(); it.next(); it.next();
            assertTrue(!it.hasNext(), "hasNext() should be false after consuming all books");
        });
        test("iterator() returns a fresh cursor each time", () -> {
            BookShelf shelf = new BookShelf(5); shelf.add(new Book("Dune"));
            BookIterator it1 = shelf.iterator(); it1.next();
            assertTrue(shelf.iterator().hasNext(), "second iterator should start from beginning");
        });
        test("can iterate a full shelf completely", () -> {
            BookShelf shelf = new BookShelf(3);
            shelf.add(new Book("A")); shelf.add(new Book("B")); shelf.add(new Book("C"));
            int count = 0; BookIterator it = shelf.iterator();
            while (it.hasNext()) { it.next(); count++; }
            assertEquals(3, count, "should iterate exactly 3 books");
        });
        test("two independent iterators traverse the same books independently", () -> {
            BookShelf shelf = new BookShelf(5);
            shelf.add(new Book("A")); shelf.add(new Book("B"));
            BookIterator it1 = shelf.iterator(); BookIterator it2 = shelf.iterator();
            assertEquals("A", it1.next().getTitle(), "it1 first book");
            assertEquals("A", it2.next().getTitle(), "it2 first book — independent of it1");
            assertEquals("B", it1.next().getTitle(), "it1 second book");
        });
        test("hasNext() is idempotent — calling it twice does not advance", () -> {
            BookShelf shelf = new BookShelf(5); shelf.add(new Book("Dune"));
            BookIterator it = shelf.iterator();
            assertTrue(it.hasNext(), "first hasNext"); assertTrue(it.hasNext(), "second hasNext");
            assertEquals("Dune", it.next().getTitle(), "next() should still return Dune");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
