class IdleState implements VendingState {
    private final VendingMachine machine;
    IdleState(VendingMachine machine) { this.machine = machine; }
    public String insertCoin() { machine.setState(new HasCoinState(machine)); return "Coin inserted"; }
    public String ejectCoin() { return "No coin to eject"; }
    public String selectProduct() { return "Please insert a coin first"; }
    public String dispense() { return "Please insert a coin first"; }
}
