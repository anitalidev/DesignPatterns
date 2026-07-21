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
            BulletPool pool = new BulletPool(5);
            assertEquals(5, pool.availableCount(), "all bullets should be available initially");
            assertEquals(0, pool.activeCount(),    "no bullets should be active initially");
        });

        test("fire() returns an active Bullet", () -> {
            BulletPool pool = new BulletPool(3);
            Bullet b = pool.fire(10, 20);
            assertTrue(b != null,    "fire() should return a non-null Bullet");
            assertTrue(b.isActive(), "fired bullet should be active");
        });

        test("fire() sets the bullet's position", () -> {
            BulletPool pool = new BulletPool(3);
            Bullet b = pool.fire(10, 20);
            assertEquals(10, b.getX(), "x should be 10");
            assertEquals(20, b.getY(), "y should be 20");
        });

        test("fire() reduces available count and increases active count", () -> {
            BulletPool pool = new BulletPool(3);
            pool.fire(0, 0);
            assertEquals(2, pool.availableCount(), "available should decrease after fire");
            assertEquals(1, pool.activeCount(),    "active should increase after fire");
        });

        test("fire() returns null when pool is exhausted", () -> {
            BulletPool pool = new BulletPool(2);
            pool.fire(0, 0);
            pool.fire(0, 0);
            Bullet b = pool.fire(0, 0);
            assertTrue(b == null, "fire() should return null when no bullets are available");
        });

        test("recycle() resets the bullet", () -> {
            BulletPool pool = new BulletPool(3);
            Bullet b = pool.fire(5, 5);
            pool.recycle(b);
            assertTrue(!b.isActive(), "recycled bullet should not be active");
        });

        test("recycle() restores available count", () -> {
            BulletPool pool = new BulletPool(3);
            Bullet b = pool.fire(0, 0);
            pool.recycle(b);
            assertEquals(3, pool.availableCount(), "available count should be restored after recycle");
            assertEquals(0, pool.activeCount(),    "active count should drop to 0 after recycle");
        });

        test("recycled bullet can be fired again", () -> {
            BulletPool pool = new BulletPool(1);
            Bullet first = pool.fire(1, 1);
            pool.recycle(first);
            Bullet second = pool.fire(2, 2);
            assertTrue(second != null,    "should be able to fire after recycling");
            assertTrue(second.isActive(), "re-fired bullet should be active");
            assertEquals(2, second.getX(), "re-fired bullet x should be updated");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
