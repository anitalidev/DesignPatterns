class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("build() returns an HttpRequest with method and url", () -> {
            HttpRequest req = new HttpRequestBuilder().method("GET").url("https://example.com").build();
            assertEquals("GET", req.method, "method should be GET");
            assertEquals("https://example.com", req.url, "url should match");
        });
        test("setters return 'this' for chaining", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            assertTrue(b.method("GET") == b, "method() should return the builder");
            assertTrue(b.url("https://example.com") == b, "url() should return the builder");
            assertTrue(b.header("X-Foo", "bar") == b, "header() should return the builder");
            assertTrue(b.body("hello") == b, "body() should return the builder");
        });
        test("headers are included in the built request", () -> {
            HttpRequest req = new HttpRequestBuilder().method("POST").url("https://example.com/api")
                .header("Content-Type", "application/json").header("Authorization", "Bearer token").build();
            assertEquals("application/json", req.headers.get("Content-Type"), "Content-Type header");
            assertEquals("Bearer token", req.headers.get("Authorization"), "Authorization header");
        });
        test("body is included in the built request", () -> {
            HttpRequest req = new HttpRequestBuilder().method("POST").url("https://example.com")
                .body("{\"key\":\"value\"}").build();
            assertEquals("{\"key\":\"value\"}", req.body, "body should match");
        });
        test("body defaults to null when not set", () -> {
            HttpRequest req = new HttpRequestBuilder().method("GET").url("https://example.com").build();
            assertTrue(req.body == null, "body should be null when not set");
        });
        test("build() throws when method is missing", () -> {
            boolean threw = false;
            try { new HttpRequestBuilder().url("https://example.com").build(); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "build() should throw when method is missing");
        });
        test("build() throws when url is missing", () -> {
            boolean threw = false;
            try { new HttpRequestBuilder().method("GET").build(); } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "build() should throw when url is missing");
        });
        test("each build() call produces an independent HttpRequest", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder().method("GET").url("https://example.com");
            HttpRequest r1 = b.build(); b.method("POST"); HttpRequest r2 = b.build();
            assertEquals("GET",  r1.method, "first request should be GET");
            assertEquals("POST", r2.method, "second request should be POST");
        });
        test("multiple headers all appear in the request", () -> {
            HttpRequest req = new HttpRequestBuilder().method("GET").url("https://example.com")
                .header("A", "1").header("B", "2").header("C", "3").build();
            assertEquals("1", req.headers.get("A"), "header A");
            assertEquals("2", req.headers.get("B"), "header B");
            assertEquals("3", req.headers.get("C"), "header C");
        });
        test("headers are empty by default", () -> {
            HttpRequest req = new HttpRequestBuilder().method("GET").url("https://example.com").build();
            assertTrue(req.headers != null && req.headers.isEmpty(), "headers should be empty when none are set");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
