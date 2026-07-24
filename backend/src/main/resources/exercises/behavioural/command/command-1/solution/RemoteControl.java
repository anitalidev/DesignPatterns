import java.util.ArrayDeque;
import java.util.Deque;

class RemoteControl {
    private final Deque<Command> history = new ArrayDeque<>();

    public void pressButton(Command command) {
        command.execute();
        history.push(command);
    }

    public void pressUndo() {
        if (!history.isEmpty()) history.pop().undo();
    }
}
