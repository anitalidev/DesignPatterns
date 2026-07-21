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

    static void assertTrue(boolean cond, String msg) {
        if (!cond) throw new AssertionError(msg);
    }

    public static void main(String[] args) {
        test("togglePower() turns on a device that is off", () -> {
            TV tv = new TV();
            RemoteControl remote = new RemoteControl(tv);
            remote.togglePower();
            assertTrue(tv.isOn(), "TV should be on after togglePower()");
        });

        test("togglePower() turns off a device that is on", () -> {
            TV tv = new TV();
            RemoteControl remote = new RemoteControl(tv);
            remote.togglePower(); // on
            remote.togglePower(); // off
            assertTrue(!tv.isOn(), "TV should be off after second togglePower()");
        });

        test("setVolume() delegates to the device", () -> {
            TV tv = new TV();
            RemoteControl remote = new RemoteControl(tv);
            remote.setVolume(50);
            assertEquals(50, tv.getVolume(), "TV volume should be 50");
        });

        test("getDevice() returns the device passed to the constructor", () -> {
            TV tv = new TV();
            RemoteControl remote = new RemoteControl(tv);
            assertTrue(remote.getDevice() == tv, "getDevice() should return the same TV instance");
        });

        test("RemoteControl works with Radio as well as TV", () -> {
            Radio radio = new Radio();
            RemoteControl remote = new RemoteControl(radio);
            remote.togglePower();
            remote.setVolume(20);
            assertTrue(radio.isOn(),          "Radio should be on");
            assertEquals(20, radio.getVolume(), "Radio volume should be 20");
        });

        test("AdvancedRemote.mute() sets volume to 0", () -> {
            TV tv = new TV();
            AdvancedRemote remote = new AdvancedRemote(tv);
            remote.setVolume(75);
            remote.mute();
            assertEquals(0, tv.getVolume(), "volume should be 0 after mute()");
        });

        test("AdvancedRemote inherits togglePower() from RemoteControl", () -> {
            Radio radio = new Radio();
            AdvancedRemote remote = new AdvancedRemote(radio);
            remote.togglePower();
            assertTrue(radio.isOn(), "AdvancedRemote should be able to toggle power");
        });

        test("AdvancedRemote mute() works on Radio too", () -> {
            Radio radio = new Radio();
            AdvancedRemote remote = new AdvancedRemote(radio);
            remote.setVolume(60);
            remote.mute();
            assertEquals(0, radio.getVolume(), "Radio volume should be 0 after mute()");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
