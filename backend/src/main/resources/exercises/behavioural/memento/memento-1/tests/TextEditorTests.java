class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("save() captures current content", () -> {
            TextEditor ed = new TextEditor(); ed.type("Hello");
            Memento m = ed.save();
            assertEquals("Hello", m.getContent(), "memento should store 'Hello'");
        });
        test("restore() resets content to saved state", () -> {
            TextEditor ed = new TextEditor(); ed.type("Hello");
            Memento m = ed.save(); ed.type(" World");
            ed.restore(m);
            assertEquals("Hello", ed.getContent(), "content should be restored to 'Hello'");
        });
        test("multiple saves and restores work correctly", () -> {
            TextEditor ed = new TextEditor();
            History h = new History();
            ed.type("A"); h.push(ed.save());
            ed.type("B"); h.push(ed.save());
            ed.type("C");
            ed.restore(h.pop()); assertEquals("AB", ed.getContent(), "after first undo");
            ed.restore(h.pop()); assertEquals("A",  ed.getContent(), "after second undo");
        });
        test("History returns mementos in LIFO order", () -> {
            History h = new History();
            h.push(new Memento("first")); h.push(new Memento("second"));
            assertEquals("second", h.pop().getContent(), "most recent memento should come out first");
        });
        test("History.pop() returns null when empty", () -> {
            History h = new History();
            assertTrue(h.pop() == null, "pop on empty history should return null");
        });
        test("typing after restore does not affect the memento", () -> {
            TextEditor ed = new TextEditor(); ed.type("Hello");
            Memento m = ed.save(); ed.restore(m); ed.type(" World");
            assertEquals("Hello", m.getContent(), "memento content should not change after restore+type");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
