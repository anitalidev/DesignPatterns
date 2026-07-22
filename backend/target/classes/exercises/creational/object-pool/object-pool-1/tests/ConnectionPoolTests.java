class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("pool starts fully available", () ->
            assertEquals(3, new ConnectionPool(3).availableCount(), "all 3 connections should be available initially"));
        test("acquire() returns a non-null Connection", () ->
            assertTrue(new ConnectionPool(2).acquire() != null, "acquire() must return a Connection"));
        test("acquire() reduces available count", () -> {
            ConnectionPool pool = new ConnectionPool(3); pool.acquire();
            assertEquals(2, pool.availableCount(), "available count should decrease after acquire");
        });
        test("each acquire() returns a different Connection", () -> {
            ConnectionPool pool = new ConnectionPool(2);
            assertTrue(pool.acquire() != pool.acquire(), "successive acquire() calls should return different connections");
        });
        test("release() restores available count", () -> {
            ConnectionPool pool = new ConnectionPool(2); Connection c = pool.acquire(); pool.release(c);
            assertEquals(2, pool.availableCount(), "available count should be restored after release");
        });
        test("released connection can be acquired again", () -> {
            ConnectionPool pool = new ConnectionPool(1);
            pool.release(pool.acquire());
            assertTrue(pool.acquire() != null, "should be able to acquire after releasing");
        });
        test("acquire() throws when pool is exhausted", () -> {
            ConnectionPool pool = new ConnectionPool(2); pool.acquire(); pool.acquire();
            boolean threw = false;
            try { pool.acquire(); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "acquire() should throw when no connections are available");
        });
        test("pool pre-fills on construction", () ->
            assertEquals(3, new ConnectionPool(3).availableCount(), "pool should be full before any acquire()"));
        test("acquire all then release all restores full availability", () -> {
            ConnectionPool pool = new ConnectionPool(3);
            Connection a = pool.acquire(); Connection b = pool.acquire(); Connection c = pool.acquire();
            pool.release(a); pool.release(b); pool.release(c);
            assertEquals(3, pool.availableCount(), "all released connections should restore full availability");
        });
        test("pool of size 1 can be used in a borrow-return cycle", () -> {
            ConnectionPool pool = new ConnectionPool(1);
            for (int i = 0; i < 5; i++) { Connection c = pool.acquire(); pool.release(c); }
            assertEquals(1, pool.availableCount(), "single-slot pool should be back to 1 after 5 cycles");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
