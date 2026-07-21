import java.util.List;

class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    static List<String[]> rows() {
        return List.of(new String[]{"Alice","30","London"}, new String[]{"Bob","25","Paris"});
    }

    public static void main(String[] args) {
        test("CSV output contains header", () -> {
            String out = new CSVExporter().export(rows());
            assertTrue(out.contains("name,age,city"), "CSV should start with header");
        });
        test("CSV output contains first row", () -> {
            String out = new CSVExporter().export(rows());
            assertTrue(out.contains("Alice,30,London"), "CSV should contain first row");
        });
        test("CSV output contains second row", () -> {
            String out = new CSVExporter().export(rows());
            assertTrue(out.contains("Bob,25,Paris"), "CSV should contain second row");
        });
        test("CSV header appears before rows", () -> {
            String out = new CSVExporter().export(rows());
            assertTrue(out.indexOf("name,age,city") < out.indexOf("Alice"), "header should come before rows");
        });
        test("JSON output opens with [", () -> {
            String out = new JSONExporter().export(rows());
            assertTrue(out.trim().startsWith("["), "JSON should start with [");
        });
        test("JSON output closes with ]", () -> {
            String out = new JSONExporter().export(rows());
            assertTrue(out.trim().endsWith("]"), "JSON should end with ]");
        });
        test("JSON output contains Alice's data", () -> {
            String out = new JSONExporter().export(rows());
            assertTrue(out.contains("Alice") && out.contains("30") && out.contains("London"), "JSON should contain Alice's data");
        });
        test("JSON output contains Bob's data", () -> {
            String out = new JSONExporter().export(rows());
            assertTrue(out.contains("Bob") && out.contains("25") && out.contains("Paris"), "JSON should contain Bob's data");
        });
        test("export() with no rows still produces header and footer", () -> {
            String csv  = new CSVExporter().export(List.of());
            String json = new JSONExporter().export(List.of());
            assertTrue(csv.contains("name"),  "CSV with no rows should still have header");
            assertTrue(json.contains("["),    "JSON with no rows should still have brackets");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
