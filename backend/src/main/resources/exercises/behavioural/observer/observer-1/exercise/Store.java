import java.util.function.Consumer;

class Store {
    private String state = "";
    private Logger logger = new Logger();
    private UI ui = new UI();

    public void setState(String newState) {
        this.state = newState;
        logger.log(state);
        ui.render(state);
    }

    public String getState() { return state; }
}
