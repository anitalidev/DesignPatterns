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
        test("SecureServiceProxy implements Service", () -> {
            Service proxy = new SecureServiceProxy(new AdminService(), "ADMIN");
            assertTrue(proxy != null, "proxy should be assignable to Service");
        });

        test("ADMIN can call getData()", () -> {
            AdminService real = new AdminService();
            Service proxy = new SecureServiceProxy(real, "ADMIN");
            assertEquals("sensitive data", proxy.getData(), "ADMIN should receive data");
        });

        test("ADMIN call reaches the real service", () -> {
            AdminService real = new AdminService();
            new SecureServiceProxy(real, "ADMIN").getData();
            assertTrue(real.getCallLog().contains("getData"), "real service should have been called");
        });

        test("ADMIN can call deleteAll()", () -> {
            AdminService real = new AdminService();
            new SecureServiceProxy(real, "ADMIN").deleteAll();
            assertTrue(real.getCallLog().contains("deleteAll"), "deleteAll() should reach real service for ADMIN");
        });

        test("non-ADMIN getData() throws UnauthorizedException", () -> {
            Service proxy = new SecureServiceProxy(new AdminService(), "USER");
            boolean threw = false;
            try { proxy.getData(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "USER role should not be able to call getData()");
        });

        test("non-ADMIN deleteAll() throws UnauthorizedException", () -> {
            Service proxy = new SecureServiceProxy(new AdminService(), "GUEST");
            boolean threw = false;
            try { proxy.deleteAll(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "GUEST role should not be able to call deleteAll()");
        });

        test("real service is NOT called when access is denied", () -> {
            AdminService real = new AdminService();
            try { new SecureServiceProxy(real, "USER").getData(); } catch (UnauthorizedException ignored) {}
            assertEquals(0, real.getCallLog().size(), "real service should not be called when access is denied");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
