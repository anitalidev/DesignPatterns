// TODO: VendingMachine manages state with a String, so every method branches on it.
// Adding a new state means editing all four methods here.
// Fix it by delegating to VendingState objects instead:
//   - replace the String field with a VendingState field, initialized to new IdleState(this)
//   - implement setState() to assign the field
//   - replace each method body with state.insertCoin() / state.ejectCoin() / state.selectProduct() / state.dispense()
class VendingMachine {
    private String state = "idle"; // TODO: replace with a VendingState field

    public void setState(VendingState newState) {
        // TODO: assign newState to the state field (after changing it to VendingState)
    }

    public String insertCoin() {
        switch (state) {
            case "idle":
                state = "hasCoin";
                return "Coin inserted";
            case "hasCoin":
                return "Coin already inserted";
            default:
                return "Please wait, dispensing in progress";
        }
    }

    public String ejectCoin() {
        switch (state) {
            case "idle":
                return "No coin to eject";
            case "hasCoin":
                state = "idle";
                return "Coin ejected";
            default:
                return "Please wait, dispensing in progress";
        }
    }

    public String selectProduct() {
        switch (state) {
            case "idle":
                return "Please insert a coin first";
            case "hasCoin":
                state = "dispensing";
                return "Product selected";
            default:
                return "Please wait, dispensing in progress";
        }
    }

    public String dispense() {
        switch (state) {
            case "idle":
                return "Please insert a coin first";
            case "hasCoin":
                return "Please select a product first";
            default:
                state = "idle";
                return "Product dispensed";
        }
    }
}
