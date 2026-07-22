class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("LightOnCommand.execute() turns the light on", () -> {
            Light l = new Light(); new LightOnCommand(l).execute();
            assertTrue(l.isOn(), "light should be on after execute()");
        });
        test("LightOnCommand.undo() turns the light off", () -> {
            Light l = new Light(); l.turnOn(); new LightOnCommand(l).undo();
            assertTrue(!l.isOn(), "light should be off after undo()");
        });
        test("LightOffCommand.execute() turns the light off", () -> {
            Light l = new Light(); l.turnOn(); new LightOffCommand(l).execute();
            assertTrue(!l.isOn(), "light should be off after execute()");
        });
        test("LightOffCommand.undo() turns the light on", () -> {
            Light l = new Light(); new LightOffCommand(l).undo();
            assertTrue(l.isOn(), "light should be on after undo()");
        });
        test("pressButton() executes the command", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl();
            r.pressButton(new LightOnCommand(l)); assertTrue(l.isOn(), "light should be on after pressButton");
        });
        test("pressUndo() reverses the last command", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl();
            r.pressButton(new LightOnCommand(l)); r.pressUndo();
            assertTrue(!l.isOn(), "light should be off after pressUndo");
        });
        test("pressUndo() reverses in LIFO order", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl();
            r.pressButton(new LightOnCommand(l)); r.pressButton(new LightOffCommand(l));
            r.pressUndo(); assertTrue(l.isOn(), "undo of LightOff should turn light back on");
        });
        test("pressUndo() on empty history does nothing", () -> {
            new RemoteControl().pressUndo(); assertTrue(true, "should not throw");
        });
        test("three commands then two undos restore correctly", () -> {
            Light l = new Light(); RemoteControl r = new RemoteControl();
            r.pressButton(new LightOnCommand(l));
            r.pressButton(new LightOffCommand(l));
            r.pressButton(new LightOnCommand(l));
            r.pressUndo(); assertTrue(!l.isOn(), "after first undo, light should be off");
            r.pressUndo(); assertTrue(l.isOn(),  "after second undo, light should be on");
        });
        test("execute() and undo() are inverses for LightOn", () -> {
            Light l = new Light(); LightOnCommand cmd = new LightOnCommand(l);
            cmd.execute(); assertTrue(l.isOn(),  "on after execute");
            cmd.undo();    assertTrue(!l.isOn(), "off after undo");
        });
        test("execute() and undo() are inverses for LightOff", () -> {
            Light l = new Light(); l.turnOn(); LightOffCommand cmd = new LightOffCommand(l);
            cmd.execute(); assertTrue(!l.isOn(), "off after execute");
            cmd.undo();    assertTrue(l.isOn(),  "on after undo");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
