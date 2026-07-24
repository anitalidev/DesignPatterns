class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("build() returns an HttpRequest with method and url", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("GET");
            b.url("https://example.com");
            HttpRequest req = b.build();
            assertEquals("GET", req.method, "method should be GET");
            assertEquals("https://example.com", req.url, "url should match");
        });
        test("headers are included in the built request", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("POST");
            b.url("https://example.com/api");
            b.header("Content-Type", "application/json");
            b.header("Authorization", "Bearer token");
            HttpRequest req = b.build();
            assertEquals("application/json", req.headers.get("Content-Type"), "Content-Type header");
            assertEquals("Bearer token", req.headers.get("Authorization"), "Authorization header");
        });
        test("body is included in the built request", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("POST");
            b.url("https://example.com");
            b.body("{\"key\":\"value\"}");
            HttpRequest req = b.build();
            assertEquals("{\"key\":\"value\"}", req.body, "body should match");
        });
        test("body defaults to null when not set", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("GET");
            b.url("https://example.com");
            HttpRequest req = b.build();
            assertTrue(req.body == null, "body should be null when not set");
        });
        test("build() throws when method is missing", () -> {
            boolean threw = false;
            try {
                HttpRequestBuilder b = new HttpRequestBuilder();
                b.url("https://example.com");
                b.build();
            } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "build() should throw when method is missing");
        });
        test("build() throws when url is missing", () -> {
            boolean threw = false;
            try {
                HttpRequestBuilder b = new HttpRequestBuilder();
                b.method("GET");
                b.build();
            } catch (IllegalStateException e) { threw = true; }
            assertTrue(threw, "build() should throw when url is missing");
        });
        test("each build() call produces an independent HttpRequest", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("GET");
            b.url("https://example.com");
            HttpRequest r1 = b.build();
            b.method("POST");
            HttpRequest r2 = b.build();
            assertEquals("GET",  r1.method, "first request should be GET");
            assertEquals("POST", r2.method, "second request should be POST");
        });
        test("multiple headers all appear in the request", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("GET");
            b.url("https://example.com");
            b.header("A", "1");
            b.header("B", "2");
            b.header("C", "3");
            HttpRequest req = b.build();
            assertEquals("1", req.headers.get("A"), "header A");
            assertEquals("2", req.headers.get("B"), "header B");
            assertEquals("3", req.headers.get("C"), "header C");
        });
        test("headers are empty by default", () -> {
            HttpRequestBuilder b = new HttpRequestBuilder();
            b.method("GET");
            b.url("https://example.com");
            HttpRequest req = b.build();
            assertTrue(req.headers != null && req.headers.isEmpty(), "headers should be empty when none are set");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
