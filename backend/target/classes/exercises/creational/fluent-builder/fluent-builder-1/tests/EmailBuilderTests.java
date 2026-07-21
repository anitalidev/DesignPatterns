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
        test("build() returns Email with 'to' set", () -> {
            Email e = new EmailBuilder().to("alice@example.com").build();
            assertEquals("alice@example.com", e.to, "'to' should match");
        });

        test("all setters return 'this' for chaining", () -> {
            EmailBuilder b = new EmailBuilder();
            assertTrue(b.to("a@b.com")    == b, "to() should return the builder");
            assertTrue(b.cc("c@d.com")    == b, "cc() should return the builder");
            assertTrue(b.subject("Hi")    == b, "subject() should return the builder");
            assertTrue(b.body("Hello")    == b, "body() should return the builder");
            assertTrue(b.attach("f.pdf")  == b, "attach() should return the builder");
        });

        test("subject and body are stored correctly", () -> {
            Email e = new EmailBuilder()
                .to("alice@example.com")
                .subject("Meeting notes")
                .body("Please find attached.")
                .build();
            assertEquals("Meeting notes",       e.subject, "subject should match");
            assertEquals("Please find attached.", e.body,  "body should match");
        });

        test("multiple cc() calls accumulate into a list", () -> {
            Email e = new EmailBuilder()
                .to("alice@example.com")
                .cc("bob@example.com")
                .cc("carol@example.com")
                .build();
            assertEquals(2, e.cc.size(), "cc list should have 2 entries");
            assertTrue(e.cc.contains("bob@example.com"),   "cc should contain bob");
            assertTrue(e.cc.contains("carol@example.com"), "cc should contain carol");
        });

        test("multiple attach() calls accumulate into a list", () -> {
            Email e = new EmailBuilder()
                .to("alice@example.com")
                .attach("report.pdf")
                .attach("photo.png")
                .build();
            assertEquals(2, e.attachments.size(), "attachments list should have 2 entries");
            assertTrue(e.attachments.contains("report.pdf"), "should contain report.pdf");
            assertTrue(e.attachments.contains("photo.png"),  "should contain photo.png");
        });

        test("cc and attachments default to empty lists", () -> {
            Email e = new EmailBuilder().to("alice@example.com").build();
            assertEquals(0, e.cc.size(),          "cc should be empty by default");
            assertEquals(0, e.attachments.size(), "attachments should be empty by default");
        });

        test("build() throws when 'to' is missing", () -> {
            boolean threw = false;
            try { new EmailBuilder().subject("Hi").build(); } catch (IllegalStateException ex) { threw = true; }
            assertTrue(threw, "build() should throw IllegalStateException when 'to' is not set");
        });

        test("full chain produces a correctly populated Email", () -> {
            Email e = new EmailBuilder()
                .to("alice@example.com")
                .cc("bob@example.com")
                .subject("Hello")
                .body("World")
                .attach("file.txt")
                .build();
            assertEquals("alice@example.com", e.to,      "to");
            assertEquals("Hello",             e.subject, "subject");
            assertEquals("World",             e.body,    "body");
            assertEquals(1, e.cc.size(),          "cc count");
            assertEquals(1, e.attachments.size(), "attachment count");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
