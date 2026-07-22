class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("togglePower() turns on a device that is off", () -> {
            TV tv = new TV(); new RemoteControl(tv).togglePower(); assertTrue(tv.isOn(), "TV should be on");
        });
        test("togglePower() turns off a device that is on", () -> {
            TV tv = new TV(); RemoteControl r = new RemoteControl(tv); r.togglePower(); r.togglePower();
            assertTrue(!tv.isOn(), "TV should be off after second togglePower()");
        });
        test("setVolume() delegates to the device", () -> {
            TV tv = new TV(); new RemoteControl(tv).setVolume(50); assertEquals(50, tv.getVolume(), "TV volume should be 50");
        });
        test("getDevice() returns the device passed to the constructor", () -> {
            TV tv = new TV(); assertTrue(new RemoteControl(tv).getDevice() == tv, "getDevice() should return the TV");
        });
        test("RemoteControl works with Radio as well as TV", () -> {
            Radio radio = new Radio(); RemoteControl r = new RemoteControl(radio);
            r.togglePower(); r.setVolume(20);
            assertTrue(radio.isOn(), "Radio should be on"); assertEquals(20, radio.getVolume(), "Radio volume should be 20");
        });
        test("AdvancedRemote.mute() sets volume to 0", () -> {
            TV tv = new TV(); AdvancedRemote r = new AdvancedRemote(tv); r.setVolume(75); r.mute();
            assertEquals(0, tv.getVolume(), "volume should be 0 after mute()");
        });
        test("AdvancedRemote inherits togglePower()", () -> {
            Radio radio = new Radio(); new AdvancedRemote(radio).togglePower();
            assertTrue(radio.isOn(), "AdvancedRemote should toggle power");
        });
        test("AdvancedRemote mute() works on Radio too", () -> {
            Radio radio = new Radio(); AdvancedRemote r = new AdvancedRemote(radio); r.setVolume(60); r.mute();
            assertEquals(0, radio.getVolume(), "Radio volume should be 0 after mute()");
        });
        test("setVolume() called twice reflects the latest value", () -> {
            TV tv = new TV(); RemoteControl r = new RemoteControl(tv);
            r.setVolume(30); r.setVolume(80); assertEquals(80, tv.getVolume(), "volume should be the last value set");
        });
        test("two remotes can control two different devices independently", () -> {
            TV tv = new TV(); Radio radio = new Radio();
            RemoteControl rt = new RemoteControl(tv); RemoteControl rr = new RemoteControl(radio);
            rt.setVolume(40); rr.setVolume(20);
            assertEquals(40, tv.getVolume(),    "TV volume should be 40");
            assertEquals(20, radio.getVolume(), "Radio volume should be 20");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
