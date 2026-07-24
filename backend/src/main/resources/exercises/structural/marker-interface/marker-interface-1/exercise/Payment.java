// TODO: implement the Auditable marker interface on Payment
class Payment {
    private final double amount;
    Payment(double amount) { this.amount = amount; }
    public double getAmount() { return amount; }
}
