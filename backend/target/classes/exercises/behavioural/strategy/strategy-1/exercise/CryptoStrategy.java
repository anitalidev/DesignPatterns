// TODO: implement CryptoStrategy
// - starts with a balance of 250.0
// - pay(amount) deducts from the balance and returns a message mentioning "Crypto" and the amount
// - getBalance() returns the current balance
class CryptoStrategy implements PaymentStrategy {
    public String pay(double amount) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public double getBalance() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
