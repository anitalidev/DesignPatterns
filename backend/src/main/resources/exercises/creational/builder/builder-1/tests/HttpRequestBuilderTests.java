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
        test("build() returns an HttpRequest with method and url set", () -> {
            HttpRequest req = new HttpRequestBuilder()
                .method("GET")
                .url("https://example.com")
                .build();
            assertEquals("GET", req.method, "method should be GET");
            assertEquals("https://example.com", req.url, "url should match");
        });

        test("setters return 'this' allowing chaining", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            assertTrue(b.method("GET") == b, "method() should return the builder");
            assertTrue(b.url("https://example.com") == b, "url() should return the builder");
            assertTrue(b.header("X-Foo", "bar") == b, "header() should return the builder");
            assertTrue(b.body("hello") == b, "body() should return the builder");
        });

        test("headers are included in the built request", () -> {
            HttpRequest req = new HttpRequestBuilder()
                .method("POST")
                .url("https://example.com/api")
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer token")
                .build();
            assertEquals("application/json", req.headers.get("Content-Type"), "Content-Type header");
            assertEquals("Bearer token", req.headers.get("Authorization"), "Authorization header");
        });

        test("body is included in the built request", () -> {
            HttpRequest req = new HttpRequestBuilder()
                .method("POST")
                .url("https://example.com")
                .body("{\"key\":\"value\"}")
                .build();
            assertEquals("{\"key\":\"value\"}", req.body, "body should match");
        });

        test("body defaults to null when not set", () -> {
            HttpRequest req = new HttpRequestBuilder()
                .method("GET")
                .url("https://example.com")
                .build();
            assertTrue(req.body == null, "body should be null when not set");
        });

        test("build() throws when method is missing", () -> {
            boolean threw = false;
            try {
                new HttpRequestBuilder().url("https://example.com").build();
            } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "build() should throw IllegalStateException when method is missing");
        });

        test("build() throws when url is missing", () -> {
            boolean threw = false;
            try {
                new HttpRequestBuilder().method("GET").build();
            } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "build() should throw IllegalStateException when url is missing");
        });

        test("each build() call produces an independent HttpRequest", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder().method("GET").url("https://example.com");
            HttpRequest r1 = b.build();
            b.method("POST");
            HttpRequest r2 = b.build();
            assertEquals("GET",  r1.method, "first request should be GET");
            assertEquals("POST", r2.method, "second request should be POST");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
