// Provided — do not edit
class PlainText implements Text {
    private final String content;
    PlainText(String content) { this.content = content; }
    public String render() { return content; }
}
