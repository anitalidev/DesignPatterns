import java.util.function.Consumer;

class Logger {
    String lastLog;
    void log(String state) { this.lastLog = state; }
}

class UI {
    String rendered;
    void render(String state) { this.rendered = state; }
}

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
