import java.util.ArrayList;
import java.util.List;

class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    static ComputerFacade buildFacade(List<String> log) {
        return new ComputerFacade(new CPU(log), new Memory(log), new HardDrive(log));
    }

    public static void main(String[] args) {
        test("pressStart() calls all three subsystems", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertEquals(3, log.size(), "pressStart() should invoke all three subsystems");
        });
        test("CPU.initialize() is called", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertTrue(log.contains("CPU.initialize"), "CPU.initialize() should be called");
        });
        test("Memory.load() is called", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertTrue(log.contains("Memory.load"), "Memory.load() should be called");
        });
        test("HardDrive.spin() is called", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertTrue(log.contains("HardDrive.spin"), "HardDrive.spin() should be called");
        });
        test("CPU.initialize() runs before Memory.load()", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertTrue(log.indexOf("CPU.initialize") < log.indexOf("Memory.load"), "CPU before Memory");
        });
        test("Memory.load() runs before HardDrive.spin()", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertTrue(log.indexOf("Memory.load") < log.indexOf("HardDrive.spin"), "Memory before HardDrive");
        });
        test("each subsystem is called exactly once per pressStart()", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertEquals(1L, log.stream().filter("CPU.initialize"::equals).count(), "CPU called once");
            assertEquals(1L, log.stream().filter("Memory.load"::equals).count(),    "Memory called once");
            assertEquals(1L, log.stream().filter("HardDrive.spin"::equals).count(), "HardDrive called once");
        });
        test("calling pressStart() twice runs all steps twice", () -> {
            List<String> log = new ArrayList<>(); ComputerFacade f = buildFacade(log);
            f.pressStart(); f.pressStart();
            assertEquals(6, log.size(), "two pressStart() calls should produce 6 log entries");
        });
        test("CPU.initialize() is always the first step", () -> {
            List<String> log = new ArrayList<>(); buildFacade(log).pressStart();
            assertEquals("CPU.initialize", log.get(0), "first step should always be CPU.initialize");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
