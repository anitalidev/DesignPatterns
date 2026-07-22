class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("getReport() returns a non-null Report", () ->
            assertTrue(new ReportGenerator("Sales").getReport() != null, "getReport() must not return null"));
        test("getReport() returns correct content", () ->
            assertEquals("Report: Sales", new ReportGenerator("Sales").getReport().content, "report content should match title"));
        test("compute() is not called before getReport()", () ->
            assertEquals(0, new ReportGenerator("Sales").computeCallCount, "compute() should not run before getReport()"));
        test("compute() is called exactly once on first getReport()", () -> {
            ReportGenerator gen = new ReportGenerator("Sales"); gen.getReport();
            assertEquals(1, gen.computeCallCount, "compute() should run exactly once");
        });
        test("compute() is not called again on subsequent getReport() calls", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            gen.getReport(); gen.getReport(); gen.getReport();
            assertEquals(1, gen.computeCallCount, "compute() should still be 1 after multiple calls");
        });
        test("getReport() returns the same Report instance every time", () -> {
            ReportGenerator gen = new ReportGenerator("Sales");
            assertTrue(gen.getReport() == gen.getReport(), "getReport() should return the same cached instance");
        });
        test("two instances each perform their own independent lazy init", () -> {
            ReportGenerator a = new ReportGenerator("Sales"); ReportGenerator b = new ReportGenerator("HR");
            a.getReport();
            assertEquals(1, a.computeCallCount, "a should have computed once");
            assertEquals(0, b.computeCallCount, "b should not have computed yet");
            b.getReport();
            assertEquals(1, b.computeCallCount, "b should now have computed once");
        });
        test("each instance caches its own report independently", () -> {
            ReportGenerator a = new ReportGenerator("Sales"); ReportGenerator b = new ReportGenerator("HR");
            assertEquals("Report: Sales", a.getReport().content, "a content");
            assertEquals("Report: HR",    b.getReport().content, "b content");
        });
        test("10 getReport() calls still result in exactly one compute()", () -> {
            ReportGenerator gen = new ReportGenerator("Finance");
            for (int i = 0; i < 10; i++) gen.getReport();
            assertEquals(1, gen.computeCallCount, "compute() should be called exactly once regardless of how many times getReport() is called");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
