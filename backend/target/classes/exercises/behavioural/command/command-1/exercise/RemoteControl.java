import java.util.ArrayDeque;
import java.util.Deque;

// TODO: RemoteControl works, but it's tightly coupled to Light — adding any new
// device means adding new methods here, and undo has to manually reverse each action.
// Fix it so RemoteControl only depends on Command:
//   - remove the Light field, the string history, and the device-specific press methods
//   - add pressButton(Command command): call execute() and push the command onto a Deque<Command>
//   - add pressUndo(): pop the last Command and call undo() on it; do nothing if history is empty
class RemoteControl {
    private final Light light;
    private final Deque<String> history = new ArrayDeque<>();

    RemoteControl(Light light) { this.light = light; }

    public void pressLightOn() {
        light.turnOn();
        history.push("on");
    }

    public void pressLightOff() {
        light.turnOff();
        history.push("off");
    }

    public void pressButton(Command command) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void pressUndo() {
        if (history.isEmpty()) return;
        String last = history.pop();
        if (last.equals("on"))  light.turnOff();
        if (last.equals("off")) light.turnOn();
    }
}
