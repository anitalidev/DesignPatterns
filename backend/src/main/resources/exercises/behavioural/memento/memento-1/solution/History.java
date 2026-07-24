import java.util.ArrayDeque;
import java.util.Deque;

class History {
    private final Deque<Memento> stack = new ArrayDeque<>();

    public void push(Memento m) {
        stack.push(m);
    }

    public Memento pop() {
        return stack.isEmpty() ? null : stack.pop();
    }
}
