// TODO: complete save() and restore()
class TextEditor {
    private String content = "";

    public void type(String text) {
        content += text;
    }

    public String getContent() {
        return content;
    }

    public Memento save() {
        // TODO: return a Memento of the current content
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void restore(Memento memento) {
        // TODO: set content to the memento's saved value
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
