class DispensingState implements VendingState {
    private final VendingMachine machine;
    DispensingState(VendingMachine machine) { this.machine = machine; }
    public String insertCoin() { return "Please wait, dispensing in progress"; }
    public String ejectCoin() { return "Please wait, dispensing in progress"; }
    public String selectProduct() { return "Please wait, dispensing in progress"; }
    public String dispense() { machine.setState(new IdleState(machine)); return "Product dispensed"; }
}
