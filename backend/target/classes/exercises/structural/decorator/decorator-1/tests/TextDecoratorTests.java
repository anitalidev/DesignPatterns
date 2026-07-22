class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }

    public static void main(String[] args) {
        test("PlainText.render() returns original content", () ->
            assertEquals("hello", new PlainText("hello").render(), "PlainText should return content unchanged"));
        test("BoldDecorator wraps content in **", () ->
            assertEquals("**hello**", new BoldDecorator(new PlainText("hello")).render(), "Bold should wrap in **"));
        test("ItalicDecorator wraps content in _", () ->
            assertEquals("_hello_", new ItalicDecorator(new PlainText("hello")).render(), "Italic should wrap in _"));
        test("UpperCaseDecorator uppercases the content", () ->
            assertEquals("HELLO", new UpperCaseDecorator(new PlainText("hello")).render(), "UpperCase should uppercase"));
        test("Bold then Italic stacks correctly", () ->
            assertEquals("_**hello**_", new ItalicDecorator(new BoldDecorator(new PlainText("hello"))).render(),
                "Italic(Bold) should nest correctly"));
        test("Italic then Bold stacks correctly", () ->
            assertEquals("**_hello_**", new BoldDecorator(new ItalicDecorator(new PlainText("hello"))).render(),
                "Bold(Italic) should nest correctly"));
        test("UpperCase applied over Bold", () ->
            assertEquals("**HELLO**", new UpperCaseDecorator(new BoldDecorator(new PlainText("hello"))).render(),
                "UpperCase should uppercase the bold-wrapped content"));
        test("all three decorators stacked", () ->
            assertEquals("**_HELLO_**", new BoldDecorator(new ItalicDecorator(new UpperCaseDecorator(new PlainText("hello")))).render(),
                "Bold(Italic(UpperCase)) should apply all three"));
        test("decorators are usable as Text references", () -> {
            Text bold = new BoldDecorator(new PlainText("x"));
            assertEquals("_**x**_", new ItalicDecorator(bold).render(), "stacked decorators through Text interface");
        });
        test("PlainText with empty string is unaffected", () ->
            assertEquals("", new PlainText("").render(), "empty PlainText should render empty"));
        test("BoldDecorator on empty PlainText produces ****", () ->
            assertEquals("****", new BoldDecorator(new PlainText("")).render(), "Bold of empty should be ****"));
        test("same decorator wrapping different PlainTexts are independent", () -> {
            Text t1 = new BoldDecorator(new PlainText("a"));
            Text t2 = new BoldDecorator(new PlainText("b"));
            assertEquals("**a**", t1.render(), "t1 should render **a**");
            assertEquals("**b**", t2.render(), "t2 should render **b**");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
