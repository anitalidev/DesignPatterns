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

    public static void main(String[] args) {
        test("PlainText.render() returns the original content", () -> {
            Text t = new PlainText("hello");
            assertEquals("hello", t.render(), "PlainText should return content unchanged");
        });

        test("BoldDecorator wraps content in **", () -> {
            Text t = new BoldDecorator(new PlainText("hello"));
            assertEquals("**hello**", t.render(), "BoldDecorator should wrap in **");
        });

        test("ItalicDecorator wraps content in _", () -> {
            Text t = new ItalicDecorator(new PlainText("hello"));
            assertEquals("_hello_", t.render(), "ItalicDecorator should wrap in _");
        });

        test("UpperCaseDecorator uppercases the content", () -> {
            Text t = new UpperCaseDecorator(new PlainText("hello"));
            assertEquals("HELLO", t.render(), "UpperCaseDecorator should uppercase");
        });

        test("Bold then Italic stacks correctly", () -> {
            Text t = new ItalicDecorator(new BoldDecorator(new PlainText("hello")));
            assertEquals("_**hello**_", t.render(), "Italic(Bold) should nest correctly");
        });

        test("Italic then Bold stacks correctly", () -> {
            Text t = new BoldDecorator(new ItalicDecorator(new PlainText("hello")));
            assertEquals("**_hello_**", t.render(), "Bold(Italic) should nest correctly");
        });

        test("UpperCase applied over Bold", () -> {
            Text t = new UpperCaseDecorator(new BoldDecorator(new PlainText("hello")));
            assertEquals("**HELLO**", t.render(), "UpperCase should uppercase the bold-wrapped content");
        });

        test("all three decorators stacked", () -> {
            Text t = new BoldDecorator(new ItalicDecorator(new UpperCaseDecorator(new PlainText("hello"))));
            assertEquals("**_HELLO_**", t.render(), "Bold(Italic(UpperCase)) should apply all three");
        });

        test("decorators are usable as Text references", () -> {
            Text bold   = new BoldDecorator(new PlainText("x"));
            Text italic = new ItalicDecorator(bold);
            assertEquals("_**x**_", italic.render(), "stacked decorators should work through Text interface");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
