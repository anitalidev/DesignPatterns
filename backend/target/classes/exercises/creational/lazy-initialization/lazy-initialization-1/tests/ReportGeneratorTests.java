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
        test("getReport() returns a non-null Report", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            assertTrue(gen.getReport() != null, "getReport() must not return null");
        });

        test("getReport() returns correct content", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            assertEquals("Report: Sales", gen.getReport().content, "report content should match title");
        });

        test("compute() is not called before getReport()", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            assertEquals(0, gen.computeCallCount, "compute() should not run before getReport() is called");
        });

        test("compute() is called exactly once on first getReport()", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            gen.getReport();
            assertEquals(1, gen.computeCallCount, "compute() should run exactly once after first getReport()");
        });

        test("compute() is not called again on subsequent getReport() calls", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            gen.getReport();
            gen.getReport();
            gen.getReport();
            assertEquals(1, gen.computeCallCount, "compute() should still be 1 after multiple getReport() calls");
        });

        test("getReport() returns the same Report instance every time", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            Report first  = gen.getReport();
            Report second = gen.getReport();
            assertTrue(first == second, "getReport() should return the same cached instance");
        });

        test("two instances each perform their own independent lazy init", () -> {
            ReportGenerator a = new ReportGenerator("Sales");
            ReportGenerator b = new ReportGenerator("HR");
            a.getReport();
            assertEquals(1, a.computeCallCount, "instance a should have computed once");
            assertEquals(0, b.computeCallCount, "instance b should not have computed yet");
            b.getReport();
            assertEquals(1, b.computeCallCount, "instance b should now have computed once");
        });

        test("each instance caches its own report independently", () -> {
            ReportGenerator a = new ReportGenerator("Sales");
            ReportGenerator b = new ReportGenerator("HR");
            assertEquals("Report: Sales", a.getReport().content, "instance a content");
            assertEquals("Report: HR",    b.getReport().content, "instance b content");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
