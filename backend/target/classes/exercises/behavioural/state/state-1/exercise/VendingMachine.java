// Provided — do not edit
interface VendingState {
    String insertCoin();
    String selectProduct();
    String dispense();
}

// TODO: implement IdleState — waiting for a coin
class IdleState implements VendingState {
    IdleState(VendingMachine machine) { /* TODO */ }
    public String insertCoin()     { throw new UnsupportedOperationException("Not yet implemented"); }
    public String selectProduct()  { throw new UnsupportedOperationException("Not yet implemented"); }
    public String dispense()       { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement HasCoinState — coin inserted, awaiting product selection
class HasCoinState implements VendingState {
    HasCoinState(VendingMachine machine) { /* TODO */ }
    public String insertCoin()     { throw new UnsupportedOperationException("Not yet implemented"); }
    public String selectProduct()  { throw new UnsupportedOperationException("Not yet implemented"); }
    public String dispense()       { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement DispensingState — dispensing the product
class DispensingState implements VendingState {
    DispensingState(VendingMachine machine) { /* TODO */ }
    public String insertCoin()     { throw new UnsupportedOperationException("Not yet implemented"); }
    public String selectProduct()  { throw new UnsupportedOperationException("Not yet implemented"); }
    public String dispense()       { throw new UnsupportedOperationException("Not yet implemented"); }
}

// Provided — do not edit
class VendingMachine {
    private VendingState state;

    VendingMachine() { state = new IdleState(this); }

    public void setState(VendingState state) { this.state = state; }

    public String insertCoin()    { return state.insertCoin(); }
    public String selectProduct() { return state.selectProduct(); }
    public String dispense()      { return state.dispense(); }
}
