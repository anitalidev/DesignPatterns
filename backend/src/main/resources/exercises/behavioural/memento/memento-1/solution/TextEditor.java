import java.util.ArrayDeque;
import java.util.Deque;

class Memento {
    private final String content;
    Memento(String content) { this.content = content; }
    public String getContent() { return content; }
}

class History {
    private final Deque<Memento> stack = new ArrayDeque<>();
    public void push(Memento m) { stack.push(m); }
    public Memento pop()        { return stack.isEmpty() ? null : stack.pop(); }
}

class TextEditor {
    private String content = "";
    public void type(String text)        { content += text; }
    public String getContent()           { return content; }
    public Memento save()                { return new Memento(content); }
    public void restore(Memento memento) { content = memento.getContent(); }
}
