class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Throwable e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertEquals(Object e, Object a, String m) { if (!e.equals(a)) throw new AssertionError(m + " — expected: " + e + ", got: " + a); }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("message reaches another user", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); User bob = new User("Bob", room);
            alice.send("Hello!"); assertTrue(!bob.getInbox().isEmpty(), "Bob should receive Alice's message — got inbox: " + bob.getInbox());
        });
        test("message includes sender name", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); User bob = new User("Bob", room);
            alice.send("Hi");
            assertTrue(bob.getInbox().get(0).contains("Alice"), "inbox entry should contain sender name — got: \"" + bob.getInbox().get(0) + "\"");
        });
        test("message includes content", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); User bob = new User("Bob", room);
            alice.send("Hello world");
            assertTrue(bob.getInbox().get(0).contains("Hello world"), "inbox entry should contain message text — got: \"" + bob.getInbox().get(0) + "\"");
        });
        test("sender does not receive their own message", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); new User("Bob", room);
            alice.send("Hello"); assertTrue(alice.getInbox().isEmpty(), "Alice should not receive her own message — got inbox: " + alice.getInbox());
        });
        test("message reaches all other users", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); User bob = new User("Bob", room); User carol = new User("Carol", room);
            alice.send("Hey everyone");
            assertTrue(!bob.getInbox().isEmpty(),   "Bob should receive message — got inbox: " + bob.getInbox());
            assertTrue(!carol.getInbox().isEmpty(), "Carol should receive message — got inbox: " + carol.getInbox());
        });
        test("multiple messages accumulate in inbox", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); User bob = new User("Bob", room);
            alice.send("First"); alice.send("Second");
            assertEquals(2, bob.getInbox().size(), "Bob should have 2 inbox messages");
        });
        test("messages from two different senders both arrive", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room); User bob = new User("Bob", room); User carol = new User("Carol", room);
            alice.send("Hi from Alice"); bob.send("Hi from Bob");
            assertTrue(carol.getInbox().size() >= 2, "Carol should receive messages from both Alice and Bob — got: " + carol.getInbox().size());
            assertTrue(carol.getInbox().stream().anyMatch(m -> m.contains("Alice")), "Carol should see Alice's message — got inbox: " + carol.getInbox());
            assertTrue(carol.getInbox().stream().anyMatch(m -> m.contains("Bob")),   "Carol should see Bob's message — got inbox: " + carol.getInbox());
        });
        test("users only receive messages sent after they join", () -> {
            ChatRoom room = new ChatRoom();
            User alice = new User("Alice", room);
            alice.send("Before Bob joins");
            User bob = new User("Bob", room);
            alice.send("After Bob joins");
            assertTrue(bob.getInbox().stream().anyMatch(m -> m.contains("After Bob joins")),
                "Bob should receive the message sent after joining — got inbox: " + bob.getInbox());
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
