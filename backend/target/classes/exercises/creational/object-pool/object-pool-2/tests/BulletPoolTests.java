class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("pool starts fully available", () -> {
            BulletPool pool = new BulletPool(5);
            assertEquals(5, pool.availableCount(), "all bullets should be available initially");
            assertEquals(0, pool.activeCount(),    "no bullets should be active initially");
        });
        test("fire() returns an active Bullet", () -> {
            Bullet b = new BulletPool(3).fire(10, 20);
            assertTrue(b != null && b.isActive(), "expected non-null active bullet — got: " + (b == null ? "null" : "inactive"));
        });
        test("fire() sets the bullet's position", () -> {
            Bullet b = new BulletPool(3).fire(10, 20);
            assertEquals(10, b.getX(), "x should be 10"); assertEquals(20, b.getY(), "y should be 20");
        });
        test("fire() reduces available and increases active count", () -> {
            BulletPool pool = new BulletPool(3); pool.fire(0, 0);
            assertEquals(2, pool.availableCount(), "available should decrease");
            assertEquals(1, pool.activeCount(),    "active should increase");
        });
        test("fire() returns null when pool is exhausted", () -> {
            BulletPool pool = new BulletPool(2); pool.fire(0,0); pool.fire(0,0);
            assertTrue(pool.fire(0, 0) == null, "fire() should return null when exhausted — got: non-null");
        });
        test("recycle() resets the bullet", () -> {
            BulletPool pool = new BulletPool(3); Bullet b = pool.fire(5, 5); pool.recycle(b);
            assertTrue(!b.isActive(), "recycled bullet should not be active");
        });
        test("recycle() restores available count", () -> {
            BulletPool pool = new BulletPool(3); Bullet b = pool.fire(0, 0); pool.recycle(b);
            assertEquals(3, pool.availableCount(), "available count should be restored");
            assertEquals(0, pool.activeCount(),    "active count should drop to 0");
        });
        test("recycled bullet can be fired again", () -> {
            BulletPool pool = new BulletPool(1); Bullet first = pool.fire(1, 1); pool.recycle(first);
            Bullet second = pool.fire(2, 2);
            assertTrue(second != null && second.isActive(), "expected non-null active bullet — got: " + (second == null ? "null" : "inactive"));
            assertEquals(2, second.getX(), "re-fired bullet x should be updated");
        });
        test("total Bullet objects never exceed pool size", () -> {
            BulletPool pool = new BulletPool(3);
            Bullet a = pool.fire(0,0); Bullet b = pool.fire(0,0); Bullet c = pool.fire(0,0);
            assertTrue(pool.fire(0,0) == null, "4th fire should return null — only 3 bullets exist");
            pool.recycle(a);
            assertTrue(pool.fire(0,0) != null, "after recycle, fire should succeed again");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
