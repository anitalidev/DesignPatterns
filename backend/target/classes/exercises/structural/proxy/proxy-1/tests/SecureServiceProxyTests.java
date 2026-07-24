class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        // ADMIN — full access
        test("ADMIN can call getData()", () ->
            assertEquals("sensitive data", new SecureServiceProxy(new AdminService(), "ADMIN").getData(), "ADMIN should receive data"));
        test("ADMIN getData() reaches the real service", () -> {
            AdminService real = new AdminService();
            new SecureServiceProxy(real, "ADMIN").getData();
            assertTrue(real.getCallLog().contains("getData"), "real service should have been called — log was: " + real.getCallLog());
        });
        test("ADMIN can call deleteAll()", () -> {
            AdminService real = new AdminService();
            new SecureServiceProxy(real, "ADMIN").deleteAll();
            assertTrue(real.getCallLog().contains("deleteAll"), "deleteAll() should reach real service for ADMIN — log was: " + real.getCallLog());
        });

        // USER — read-only
        test("USER can call getData()", () ->
            assertEquals("sensitive data", new SecureServiceProxy(new AdminService(), "USER").getData(), "USER should receive data"));
        test("USER getData() reaches the real service", () -> {
            AdminService real = new AdminService();
            new SecureServiceProxy(real, "USER").getData();
            assertTrue(real.getCallLog().contains("getData"), "real service should have been called for USER — log was: " + real.getCallLog());
        });
        test("USER cannot call deleteAll()", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "USER").deleteAll(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "expected UnauthorizedException to be thrown, but no exception was thrown");
        });
        test("real service is NOT called when USER tries deleteAll()", () -> {
            AdminService real = new AdminService();
            try { new SecureServiceProxy(real, "USER").deleteAll(); } catch (UnauthorizedException ignored) {}
            assertTrue(real.getCallLog().isEmpty(), "real service should not be called when USER is denied — log was: " + real.getCallLog());
        });

        // GUEST — no access
        test("GUEST cannot call getData()", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "GUEST").getData(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "expected UnauthorizedException to be thrown, but no exception was thrown");
        });
        test("GUEST cannot call deleteAll()", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "GUEST").deleteAll(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "expected UnauthorizedException to be thrown, but no exception was thrown");
        });
        test("real service is NOT called when access is denied", () -> {
            AdminService real = new AdminService();
            try { new SecureServiceProxy(real, "GUEST").getData(); } catch (UnauthorizedException ignored) {}
            assertTrue(real.getCallLog().isEmpty(), "real service should not be called when access is denied — log was: " + real.getCallLog());
        });

        // Edge cases
        test("empty-string role is unauthorized", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "").getData(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "expected UnauthorizedException to be thrown, but no exception was thrown");
        });
        test("role check is case-sensitive", () -> {
            boolean threw = false;
            try { new SecureServiceProxy(new AdminService(), "admin").deleteAll(); } catch (UnauthorizedException e) { threw = true; }
            assertTrue(threw, "expected UnauthorizedException to be thrown, but no exception was thrown");
        });
        test("SecureServiceProxy implements Service", () -> {
            Service proxy = new SecureServiceProxy(new AdminService(), "ADMIN");
            assertTrue(proxy != null, "proxy should be assignable to Service");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
