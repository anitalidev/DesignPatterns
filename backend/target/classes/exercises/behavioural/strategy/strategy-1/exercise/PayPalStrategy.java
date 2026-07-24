// TODO: implement PayPalStrategy
// - starts with a balance of 500.0
// - pay(amount) deducts from the balance and returns a message mentioning "PayPal" and the amount
// - getBalance() returns the current balance
class PayPalStrategy implements PaymentStrategy {
    public String pay(double amount) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public double getBalance() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
