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

    static void assertEquals(String expected, String actual, String msg) {
        if (!expected.equals(actual)) throw new AssertionError(msg + " — expected: " + expected + ", got: " + actual);
    }

    static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        test("MacUIFactory creates a MacButton", () -> {
            UIFactory factory = new MacUIFactory();
            Button btn = factory.createButton();
            assertEquals("MacButton", btn.render(), "MacUIFactory.createButton() should return a MacButton");
        });

        test("MacUIFactory creates a MacCheckbox", () -> {
            UIFactory factory = new MacUIFactory();
            Checkbox cb = factory.createCheckbox();
            assertEquals("MacCheckbox", cb.render(), "MacUIFactory.createCheckbox() should return a MacCheckbox");
        });

        test("WindowsUIFactory creates a WindowsButton", () -> {
            UIFactory factory = new WindowsUIFactory();
            Button btn = factory.createButton();
            assertEquals("WindowsButton", btn.render(), "WindowsUIFactory.createButton() should return a WindowsButton");
        });

        test("WindowsUIFactory creates a WindowsCheckbox", () -> {
            UIFactory factory = new WindowsUIFactory();
            Checkbox cb = factory.createCheckbox();
            assertEquals("WindowsCheckbox", cb.render(), "WindowsUIFactory.createCheckbox() should return a WindowsCheckbox");
        });

        test("Application uses Mac factory correctly", () -> {
            Application app = new Application(new MacUIFactory());
            assertEquals("MacButton",   app.buildButton().render(),   "Application with MacUIFactory should build MacButton");
            assertEquals("MacCheckbox", app.buildCheckbox().render(), "Application with MacUIFactory should build MacCheckbox");
        });

        test("Application uses Windows factory correctly", () -> {
            Application app = new Application(new WindowsUIFactory());
            assertEquals("WindowsButton",   app.buildButton().render(),   "Application with WindowsUIFactory should build WindowsButton");
            assertEquals("WindowsCheckbox", app.buildCheckbox().render(), "Application with WindowsUIFactory should build WindowsCheckbox");
        });

        test("Application constructor accepts UIFactory (not a concrete class)", () -> {
            UIFactory mac  = new MacUIFactory();
            UIFactory win  = new WindowsUIFactory();
            Application a1 = new Application(mac);
            Application a2 = new Application(win);
            assertTrue(a1 != null && a2 != null, "Should accept any UIFactory implementation");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
