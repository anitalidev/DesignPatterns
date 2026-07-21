interface VendingState {
    String insertCoin();
    String selectProduct();
    String dispense();
}

class IdleState implements VendingState {
    private final VendingMachine machine;
    IdleState(VendingMachine machine) { this.machine = machine; }
    public String insertCoin()    { machine.setState(new HasCoinState(machine)); return "Coin inserted"; }
    public String selectProduct() { return "Please insert a coin first"; }
    public String dispense()      { return "Please insert a coin first"; }
}

class HasCoinState implements VendingState {
    private final VendingMachine machine;
    HasCoinState(VendingMachine machine) { this.machine = machine; }
    public String insertCoin()    { return "Coin already inserted"; }
    public String selectProduct() { machine.setState(new DispensingState(machine)); return "Product selected"; }
    public String dispense()      { return "Please select a product first"; }
}

class DispensingState implements VendingState {
    private final VendingMachine machine;
    DispensingState(VendingMachine machine) { this.machine = machine; }
    public String insertCoin()    { return "Please wait, dispensing in progress"; }
    public String selectProduct() { return "Please wait, dispensing in progress"; }
    public String dispense()      { machine.setState(new IdleState(machine)); return "Product dispensed"; }
}

class VendingMachine {
    private VendingState state;
    VendingMachine() { state = new IdleState(this); }
    public void setState(VendingState state) { this.state = state; }
    public String insertCoin()    { return state.insertCoin(); }
    public String selectProduct() { return state.selectProduct(); }
    public String dispense()      { return state.dispense(); }
}
