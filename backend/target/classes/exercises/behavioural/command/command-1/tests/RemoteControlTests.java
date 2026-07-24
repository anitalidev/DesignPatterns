class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("LightOnCommand.execute() turns the light on", () -> {
            Light l = new Light(); new LightOnCommand(l).execute();
            assertTrue(l.isOn(), "expected light ON, got: OFF");
        });
        test("LightOnCommand.undo() turns the light off", () -> {
            Light l = new Light(); l.turnOn(); new LightOnCommand(l).undo();
            assertTrue(!l.isOn(), "expected light OFF, got: ON");
        });
        test("LightOffCommand.execute() turns the light off", () -> {
            Light l = new Light(); l.turnOn(); new LightOffCommand(l).execute();
            assertTrue(!l.isOn(), "expected light OFF, got: ON");
        });
        test("LightOffCommand.undo() turns the light on", () -> {
            Light l = new Light(); new LightOffCommand(l).undo();
            assertTrue(l.isOn(), "expected light ON, got: OFF");
        });
        test("pressButton() executes the command", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl(l);
            r.pressButton(new LightOnCommand(l));
            assertTrue(l.isOn(), "expected light ON, got: OFF");
        });
        test("pressUndo() reverses the last command", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl(l);
            r.pressButton(new LightOnCommand(l)); r.pressUndo();
            assertTrue(!l.isOn(), "expected light OFF, got: ON");
        });
        test("pressUndo() reverses in LIFO order", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl(l);
            r.pressButton(new LightOnCommand(l)); r.pressButton(new LightOffCommand(l));
            r.pressUndo(); assertTrue(l.isOn(), "expected light ON, got: OFF");
        });
        test("pressUndo() on empty history does nothing", () -> {
            Light l = new Light(); new RemoteControl(l).pressUndo(); assertTrue(true, "should not throw");
        });
        test("three commands then two undos restore correctly", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl(l);
            r.pressButton(new LightOnCommand(l));
            r.pressButton(new LightOffCommand(l));
            r.pressButton(new LightOnCommand(l));
            r.pressUndo(); assertTrue(!l.isOn(), "expected light OFF, got: ON");
            r.pressUndo(); assertTrue(l.isOn(),  "expected light ON, got: OFF");
        });
        test("execute() and undo() are inverses for LightOn", () -> {
            Light l = new Light(); LightOnCommand cmd = new LightOnCommand(l);
            cmd.execute(); assertTrue(l.isOn(),  "expected light ON, got: OFF");
            cmd.undo();    assertTrue(!l.isOn(), "expected light OFF, got: ON");
        });
        test("execute() and undo() are inverses for LightOff", () -> {
            Light l = new Light(); l.turnOn(); LightOffCommand cmd = new LightOffCommand(l);
            cmd.execute(); assertTrue(!l.isOn(), "expected light OFF, got: ON");
            cmd.undo();    assertTrue(l.isOn(),  "expected light ON, got: OFF");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
