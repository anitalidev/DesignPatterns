interface PaymentStrategy {
    String pay(double amount);
}

class CreditCardStrategy implements PaymentStrategy {
    public String pay(double amount) { return "Paid " + amount + " via Credit Card"; }
}

class PayPalStrategy implements PaymentStrategy {
    public String pay(double amount) { return "Paid " + amount + " via PayPal"; }
}

class CryptoStrategy implements PaymentStrategy {
    public String pay(double amount) { return "Paid " + amount + " via Crypto"; }
}

class PaymentProcessor {
    private PaymentStrategy strategy;
    PaymentProcessor(PaymentStrategy strategy) { this.strategy = strategy; }
    public void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }
    public String pay(double amount) { return strategy.pay(amount); }
}
