class TestRunner {
    static int passed = 0, failed = 0;
    static void test(String name, Runnable fn) {
        try { fn.run(); System.out.println("PASS: " + name); passed++; }
        catch (Exception | AssertionError e) { System.out.println("FAIL: " + name + " | " + e.getMessage()); failed++; }
    }
    static void assertTrue(boolean c, String m) { if (!c) throw new AssertionError(m); }

    public static void main(String[] args) {
        test("CreditCardStrategy.pay() mentions Credit Card", () -> {
            String r = new CreditCardStrategy().pay(50.0);
            assertTrue(r.toLowerCase().contains("credit"), "result should mention Credit Card");
        });
        test("CreditCardStrategy.pay() includes the amount", () -> {
            String r = new CreditCardStrategy().pay(99.99);
            assertTrue(r.contains("99.99"), "result should contain the amount");
        });
        test("PayPalStrategy.pay() mentions PayPal", () -> {
            String r = new PayPalStrategy().pay(50.0);
            assertTrue(r.toLowerCase().contains("paypal"), "result should mention PayPal");
        });
        test("CryptoStrategy.pay() mentions Crypto", () -> {
            String r = new CryptoStrategy().pay(50.0);
            assertTrue(r.toLowerCase().contains("crypto"), "result should mention Crypto");
        });
        test("PaymentProcessor delegates to CreditCard strategy", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            assertTrue(p.pay(100.0).toLowerCase().contains("credit"), "processor should use CreditCard");
        });
        test("setStrategy() swaps to PayPal", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            p.setStrategy(new PayPalStrategy());
            assertTrue(p.pay(50.0).toLowerCase().contains("paypal"), "processor should use PayPal after swap");
        });
        test("setStrategy() swaps to Crypto", () -> {
            PaymentProcessor p = new PaymentProcessor(new CreditCardStrategy());
            p.setStrategy(new CryptoStrategy());
            assertTrue(p.pay(50.0).toLowerCase().contains("crypto"), "processor should use Crypto after swap");
        });
        test("each strategy is independently usable as PaymentStrategy", () -> {
            PaymentStrategy cc     = new CreditCardStrategy();
            PaymentStrategy paypal = new PayPalStrategy();
            PaymentStrategy crypto = new CryptoStrategy();
            assertTrue(cc != null && paypal != null && crypto != null, "all should be assignable to PaymentStrategy");
        });

        System.out.println("---");
        System.out.println(passed + "/" + (passed + failed) + " passed");
    }
}
