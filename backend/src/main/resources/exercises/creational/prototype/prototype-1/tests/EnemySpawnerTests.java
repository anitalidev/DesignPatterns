import java.util.List;

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
        test("clone() returns a non-null Enemy", () -> {
            Enemy proto = new Enemy("Orc", 100, 5, List.of("axe"));
            assertTrue(proto.clone() != null, "clone() must not return null");
        });

        test("clone() returns a different object", () -> {
            Enemy proto = new Enemy("Orc", 100, 5, List.of("axe"));
            assertTrue(proto.clone() != proto, "clone() must return a new instance, not the same object");
        });

        test("clone copies type, health, and speed", () -> {
            Enemy proto = new Enemy("Orc", 100, 5, List.of("axe"));
            Enemy copy  = proto.clone();
            assertEquals("Orc", copy.getType(),   "type should match");
            assertEquals(100,   copy.getHealth(), "health should match");
            assertEquals(5,     copy.getSpeed(),  "speed should match");
        });

        test("clone copies the weapons list", () -> {
            Enemy proto = new Enemy("Orc", 100, 5, List.of("axe", "shield"));
            Enemy copy  = proto.clone();
            assertTrue(copy.getWeapons().contains("axe"),    "clone should have axe");
            assertTrue(copy.getWeapons().contains("shield"), "clone should have shield");
        });

        test("adding a weapon to a clone does not affect the prototype", () -> {
            Enemy proto = new Enemy("Orc", 100, 5, List.of("axe"));
            Enemy copy  = proto.clone();
            copy.addWeapon("sword");
            assertTrue(!proto.getWeapons().contains("sword"), "prototype weapons should not change when clone is modified");
        });

        test("adding a weapon to the prototype does not affect an existing clone", () -> {
            Enemy proto = new Enemy("Orc", 100, 5, List.of("axe"));
            Enemy copy  = proto.clone();
            proto.addWeapon("bow");
            assertTrue(!copy.getWeapons().contains("bow"), "clone weapons should not change when prototype is modified");
        });

        test("each spawn() produces an independent enemy", () -> {
            Enemy proto = new Enemy("Goblin", 50, 8, List.of("dagger"));
            EnemySpawner spawner = new EnemySpawner(proto);
            Enemy e1 = spawner.spawn();
            Enemy e2 = spawner.spawn();
            assertTrue(e1 != e2, "each spawn() should return a different instance");
            e1.setHealth(0);
            assertEquals(50, e2.getHealth(), "modifying one spawn should not affect another");
        });

        test("spawner does not modify the prototype", () -> {
            Enemy proto = new Enemy("Troll", 200, 3, List.of("club"));
            EnemySpawner spawner = new EnemySpawner(proto);
            spawner.spawn();
            spawner.spawn();
            assertEquals(200, proto.getHealth(), "prototype health should be unchanged after spawning");
            assertEquals(1, proto.getWeapons().size(), "prototype weapons should be unchanged after spawning");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
