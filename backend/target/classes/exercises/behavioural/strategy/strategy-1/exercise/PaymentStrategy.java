// TODO: define PaymentStrategy interface with pay(double amount) returning String

// TODO: implement CreditCardStrategy — returns a message mentioning "Credit Card" and the amount
class CreditCardStrategy {
    public String pay(double amount) { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement PayPalStrategy — returns a message mentioning "PayPal" and the amount
class PayPalStrategy {
    public String pay(double amount) { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement CryptoStrategy — returns a message mentioning "Crypto" and the amount
class CryptoStrategy {
    public String pay(double amount) { throw new UnsupportedOperationException("Not yet implemented"); }
}

// Provided — do not edit
class PaymentProcessor {
    private PaymentStrategy strategy;

    PaymentProcessor(PaymentStrategy strategy) { this.strategy = strategy; }

    public void setStrategy(PaymentStrategy strategy) { this.strategy = strategy; }

    public String pay(double amount) { return strategy.pay(amount); }
}
