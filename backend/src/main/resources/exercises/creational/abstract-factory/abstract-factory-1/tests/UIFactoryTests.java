class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(String e, String a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("MacUIFactory creates a MacButton", () ->
            assertEquals("MacButton", new MacUIFactory().createButton().render(), "MacUIFactory.createButton()"));
        test("MacUIFactory creates a MacCheckbox", () ->
            assertEquals("MacCheckbox", new MacUIFactory().createCheckbox().render(), "MacUIFactory.createCheckbox()"));
        test("WindowsUIFactory creates a WindowsButton", () ->
            assertEquals("WindowsButton", new WindowsUIFactory().createButton().render(), "WindowsUIFactory.createButton()"));
        test("WindowsUIFactory creates a WindowsCheckbox", () ->
            assertEquals("WindowsCheckbox", new WindowsUIFactory().createCheckbox().render(), "WindowsUIFactory.createCheckbox()"));
        test("Application uses Mac factory correctly", () -> {
            Application app = new Application(new MacUIFactory());
            assertEquals("MacButton",   app.buildButton().render(),   "Mac button");
            assertEquals("MacCheckbox", app.buildCheckbox().render(), "Mac checkbox");
        });
        test("Application uses Windows factory correctly", () -> {
            Application app = new Application(new WindowsUIFactory());
            assertEquals("WindowsButton",   app.buildButton().render(),   "Windows button");
            assertEquals("WindowsCheckbox", app.buildCheckbox().render(), "Windows checkbox");
        });
        test("Application constructor accepts UIFactory (not a concrete class)", () -> {
            Application a1 = new Application(new MacUIFactory());
            Application a2 = new Application(new WindowsUIFactory());
            assertTrue(a1 != null && a2 != null, "should accept any UIFactory implementation");
        });
        test("Mac and Windows factories return different button instances", () -> {
            Button mac = new MacUIFactory().createButton(); Button win = new WindowsUIFactory().createButton();
            assertTrue(!mac.render().equals(win.render()), "Mac and Windows buttons should render differently");
        });
        test("switching factory on Application changes all components", () -> {
            Application mac = new Application(new MacUIFactory());
            Application win = new Application(new WindowsUIFactory());
            assertTrue(!mac.buildButton().render().equals(win.buildButton().render()), "factories should produce different buttons");
            assertTrue(!mac.buildCheckbox().render().equals(win.buildCheckbox().render()), "factories should produce different checkboxes");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
