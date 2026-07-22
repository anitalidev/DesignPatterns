class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("SecureServiceProxy implements Service", () -> {
            Service proxy = new SecureServiceProxy(new AdminService(), "ADMIN");
            assertTrue(proxy != null, "proxy should be assignable to Service");
        });
        test("ADMIN can call getData()", () ->
            assertEquals("sensitive data", new SecureServiceProxy(new AdminService(), "ADMIN").getData(), "ADMIN should receive data"));
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
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "USER").getData(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "USER should not be able to call getData()");
        });
        test("non-ADMIN deleteAll() throws UnauthorizedException", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "GUEST").deleteAll(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "GUEST should not be able to call deleteAll()");
        });
        test("real service is NOT called when access is denied", () -> {
            AdminService real = new AdminService();
            try { new SecureServiceProxy(real, "USER").getData(); } catch (UnauthorizedException ignored) {}
            assertEquals(0, real.getCallLog().size(), "real service should not be called when access is denied");
        });
        test("empty-string role is unauthorized", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "").getData(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "empty role should not be authorized");
        });
        test("ADMIN role is case-sensitive", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "admin").getData(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "'admin' (lowercase) should not be authorized as 'ADMIN'");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
