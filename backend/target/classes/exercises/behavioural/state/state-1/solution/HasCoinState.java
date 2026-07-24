class HasCoinState implements VendingState {
    private final VendingMachine machine;
    HasCoinState(VendingMachine machine) { this.machine = machine; }
    public String insertCoin() { return "Coin already inserted"; }
    public String ejectCoin() { machine.setState(new IdleState(machine)); return "Coin ejected"; }
    public String selectProduct() { machine.setState(new DispensingState(machine)); return "Product selected"; }
    public String dispense() { return "Please select a product first"; }
}
