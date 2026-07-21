import java.util.ArrayList;
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

    static ComputerFacade buildFacade(List<String> log) {
        return new ComputerFacade(new CPU(log), new Memory(log), new HardDrive(log));
    }

    public static void main(String[] args) {
        test("pressStart() calls all three subsystems", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            assertEquals(3, log.size(), "pressStart() should invoke all three subsystems");
        });

        test("CPU.initialize() is called", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            assertTrue(log.contains("CPU.initialize"), "CPU.initialize() should be called");
        });

        test("Memory.load() is called", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            assertTrue(log.contains("Memory.load"), "Memory.load() should be called");
        });

        test("HardDrive.spin() is called", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            assertTrue(log.contains("HardDrive.spin"), "HardDrive.spin() should be called");
        });

        test("CPU.initialize() runs before Memory.load()", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            assertTrue(log.indexOf("CPU.initialize") < log.indexOf("Memory.load"),
                "CPU should initialise before Memory");
        });

        test("Memory.load() runs before HardDrive.spin()", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            assertTrue(log.indexOf("Memory.load") < log.indexOf("HardDrive.spin"),
                "Memory should load before HardDrive spins");
        });

        test("each subsystem is called exactly once per pressStart()", () -> {
            List<String> log = new ArrayList<>();
            buildFacade(log).pressStart();
            long cpuCount = log.stream().filter(s -> s.equals("CPU.initialize")).count();
            long memCount = log.stream().filter(s -> s.equals("Memory.load")).count();
            long hdCount  = log.stream().filter(s -> s.equals("HardDrive.spin")).count();
            assertEquals(1L, cpuCount, "CPU.initialize() should be called exactly once");
            assertEquals(1L, memCount, "Memory.load() should be called exactly once");
            assertEquals(1L, hdCount,  "HardDrive.spin() should be called exactly once");
        });

        test("calling pressStart() twice runs all steps twice", () -> {
            List<String> log = new ArrayList<>();
            ComputerFacade facade = buildFacade(log);
            facade.pressStart();
            facade.pressStart();
            assertEquals(6, log.size(), "two pressStart() calls should produce 6 log entries");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
