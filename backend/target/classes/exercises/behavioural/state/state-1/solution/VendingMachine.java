class VendingMachine {
    private VendingState state;
    VendingMachine() { state = new IdleState(this); }
    public void setState(VendingState state) { this.state = state; }
    public String insertCoin() { return state.insertCoin(); }
    public String ejectCoin() { return state.ejectCoin(); }
    public String selectProduct() { return state.selectProduct(); }
    public String dispense() { return state.dispense(); }
}
