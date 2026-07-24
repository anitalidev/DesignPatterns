class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("build() returns Email with 'to' set", () ->
            assertEquals("alice@example.com", new EmailBuilder().to("alice@example.com").build().to, "'to' should match"));
        test("all setters return 'this' for chaining", () -> {
            EmailBuilder b = new EmailBuilder();
            assertTrue(b.to("a@b.com")   == b, "to()");
            assertTrue(b.cc("c@d.com")   == b, "cc()");
            assertTrue(b.subject("Hi")   == b, "subject()");
            assertTrue(b.body("Hello")   == b, "body()");
            assertTrue(b.attach("f.pdf") == b, "attach()");
        });
        test("subject and body are stored correctly", () -> {
            Email e = new EmailBuilder().to("a@b.com").subject("Meeting notes").body("Please find attached.").build();
            assertEquals("Meeting notes",        e.subject, "subject");
            assertEquals("Please find attached.", e.body,   "body");
        });
        test("multiple cc() calls accumulate", () -> {
            Email e = new EmailBuilder().to("a@b.com").cc("bob@example.com").cc("carol@example.com").build();
            assertEquals(2, e.cc.size(), "cc list should have 2 entries");
            assertTrue(e.cc.contains("bob@example.com"),   "cc should contain bob");
            assertTrue(e.cc.contains("carol@example.com"), "cc should contain carol");
        });
        test("multiple attach() calls accumulate", () -> {
            Email e = new EmailBuilder().to("a@b.com").attach("report.pdf").attach("photo.png").build();
            assertEquals(2, e.attachments.size(), "attachments list should have 2 entries");
        });
        test("cc and attachments default to empty lists", () -> {
            Email e = new EmailBuilder().to("a@b.com").build();
            assertEquals(0, e.cc.size(),          "cc should be empty by default");
            assertEquals(0, e.attachments.size(), "attachments should be empty by default");
        });
        test("build() throws when 'to' is missing", () -> {
            boolean threw = false;
            try { new EmailBuilder().subject("Hi").build(); } catch (IllegalStateException ex) { threw = true; }
            assertTrue(threw, "build() should throw when 'to' is not set");
        });
        test("full chain produces a correctly populated Email", () -> {
            Email e = new EmailBuilder().to("a@b.com").cc("bob@b.com").subject("Hello").body("World").attach("f.txt").build();
            assertEquals("a@b.com", e.to, "to"); assertEquals("Hello", e.subject, "subject");
            assertEquals("World",   e.body, "body");
            assertEquals(1, e.cc.size(), "cc count"); assertEquals(1, e.attachments.size(), "attachment count");
        });
        test("two builders are independent", () -> {
            EmailBuilder b1 = new EmailBuilder().to("a@b.com").subject("First");
            EmailBuilder b2 = new EmailBuilder().to("c@d.com").subject("Second");
            assertEquals("First",  b1.build().subject, "b1 subject");
            assertEquals("Second", b2.build().subject, "b2 subject");
        });
        test("body defaults to null or empty when not set", () -> {
            Email e = new EmailBuilder().to("a@b.com").build();
            assertTrue(e.body == null || e.body.isEmpty(), "body should be absent when not set");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
