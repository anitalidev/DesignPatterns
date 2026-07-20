import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

class Store {
    private String state = "";
    private final List<Consumer<String>> observers = new ArrayList<>();

    public void subscribe(Consumer<String> fn) { observers.add(fn); }
    public void unsubscribe(Consumer<String> fn) { observers.remove(fn); }

    public void setState(String newState) {
        this.state = newState;
        for (Consumer<String> fn : observers) fn.accept(state);
    }

    public String getState() { return state; }
}
