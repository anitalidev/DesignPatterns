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
        test("CSV output contains header", () ->
            assertTrue(new CSVExporter().export(rows()).contains("name,age,city"), "CSV should have header"));
        test("CSV output contains first row", () ->
            assertTrue(new CSVExporter().export(rows()).contains("Alice,30,London"), "CSV should contain first row"));
        test("CSV output contains second row", () ->
            assertTrue(new CSVExporter().export(rows()).contains("Bob,25,Paris"), "CSV should contain second row"));
        test("CSV header appears before rows", () -> {
            String out = new CSVExporter().export(rows());
            assertTrue(out.indexOf("name,age,city") < out.indexOf("Alice"), "header should come before rows");
        });
        test("CSV rows are newline-separated", () -> {
            String out = new CSVExporter().export(rows());
            assertTrue(out.contains("\n"), "CSV output should contain newlines");
        });
        test("JSON output opens with [", () ->
            assertTrue(new JSONExporter().export(rows()).trim().startsWith("["), "JSON should start with ["));
        test("JSON output closes with ]", () ->
            assertTrue(new JSONExporter().export(rows()).trim().endsWith("]"), "JSON should end with ]"));
        test("JSON output contains Alice's data", () -> {
            String out = new JSONExporter().export(rows());
            assertTrue(out.contains("Alice") && out.contains("30") && out.contains("London"), "JSON should contain Alice's data");
        });
        test("JSON output contains Bob's data", () -> {
            String out = new JSONExporter().export(rows());
            assertTrue(out.contains("Bob") && out.contains("25") && out.contains("Paris"), "JSON should contain Bob's data");
        });
        test("export() with no rows still produces header and footer", () -> {
            assertTrue(new CSVExporter().export(List.of()).contains("name"), "CSV with no rows should have header");
            assertTrue(new JSONExporter().export(List.of()).contains("["),   "JSON with no rows should have brackets");
        });
        test("single-row CSV contains exactly that row", () -> {
            String out = new CSVExporter().export(List.of(new String[]{"Eve","28","Berlin"}));
            assertTrue(out.contains("Eve,28,Berlin"), "single-row CSV should contain the row");
        });
        test("single-row JSON is valid JSON array", () -> {
            String out = new JSONExporter().export(List.of(new String[]{"Eve","28","Berlin"})).trim();
            assertTrue(out.startsWith("[") && out.endsWith("]"), "single-row JSON should still be a valid array");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
