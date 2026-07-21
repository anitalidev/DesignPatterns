import java.util.ArrayDeque;
import java.util.Deque;

// TODO: implement Memento — stores a snapshot of the editor's content
class Memento {
    Memento(String content) {
        // TODO
    }
    public String getContent() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement History — a LIFO stack of Mementos
class History {
    public void push(Memento memento) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    // Returns the most recent Memento, or null if empty
    public Memento pop() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: complete save() and restore()
class TextEditor {
    private String content = "";

    public void type(String text)       { content += text; }
    public String getContent()          { return content; }

    public Memento save() {
        // TODO: return a Memento of the current content
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void restore(Memento memento) {
        // TODO: set content to the memento's saved value
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
