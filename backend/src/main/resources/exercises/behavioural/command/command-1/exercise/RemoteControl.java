import java.util.ArrayDeque;
import java.util.Deque;

// Provided — do not edit
interface Command {
    void execute();
    void undo();
}

// Provided — do not edit
class Light {
    private boolean on = false;
    public void turnOn()    { on = true; }
    public void turnOff()   { on = false; }
    public boolean isOn()   { return on; }
}

// TODO: implement LightOnCommand
class LightOnCommand implements Command {
    LightOnCommand(Light light) {
        // TODO
    }
    public void execute() { throw new UnsupportedOperationException("Not yet implemented"); }
    public void undo()    { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement LightOffCommand
class LightOffCommand implements Command {
    LightOffCommand(Light light) {
        // TODO
    }
    public void execute() { throw new UnsupportedOperationException("Not yet implemented"); }
    public void undo()    { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: implement RemoteControl
class RemoteControl {
    // TODO: add a history stack

    public void pressButton(Command command) {
        // TODO: execute the command and push it onto the history stack
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void pressUndo() {
        // TODO: pop the last command and call undo(); do nothing if history is empty
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
