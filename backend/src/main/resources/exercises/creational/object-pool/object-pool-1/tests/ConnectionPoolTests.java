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
        test("pool starts fully available", () -> {
            ConnectionPool pool = new ConnectionPool(3);
            assertEquals(3, pool.availableCount(), "all 3 connections should be available initially");
        });

        test("acquire() returns a non-null Connection", () -> {
            ConnectionPool pool = new ConnectionPool(2);
            assertTrue(pool.acquire() != null, "acquire() must return a Connection");
        });

        test("acquire() reduces available count", () -> {
            ConnectionPool pool = new ConnectionPool(3);
            pool.acquire();
            assertEquals(2, pool.availableCount(), "available count should decrease after acquire");
        });

        test("each acquire() returns a different Connection", () -> {
            ConnectionPool pool = new ConnectionPool(2);
            Connection a = pool.acquire();
            Connection b = pool.acquire();
            assertTrue(a != b, "successive acquire() calls should return different connections");
        });

        test("release() restores available count", () -> {
            ConnectionPool pool = new ConnectionPool(2);
            Connection c = pool.acquire();
            pool.release(c);
            assertEquals(2, pool.availableCount(), "available count should be restored after release");
        });

        test("released connection can be acquired again", () -> {
            ConnectionPool pool = new ConnectionPool(1);
            Connection first = pool.acquire();
            pool.release(first);
            Connection second = pool.acquire();
            assertTrue(second != null, "should be able to acquire after releasing");
        });

        test("acquire() throws when pool is exhausted", () -> {
            ConnectionPool pool = new ConnectionPool(2);
            pool.acquire();
            pool.acquire();
            boolean threw = false;
            try { pool.acquire(); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "acquire() should throw when no connections are available");
        });

        test("pool pre-fills on construction — no connections created on acquire()", () -> {
            ConnectionPool pool = new ConnectionPool(3);
            assertEquals(3, pool.availableCount(), "pool should be full before any acquire()");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
