interface Text {
    String render();
}

class PlainText implements Text {
    private final String content;
    PlainText(String content) { this.content = content; }
    public String render() { return content; }
}

class BoldDecorator implements Text {
    private final Text inner;
    BoldDecorator(Text inner) { this.inner = inner; }
    public String render() { return "**" + inner.render() + "**"; }
}

class ItalicDecorator implements Text {
    private final Text inner;
    ItalicDecorator(Text inner) { this.inner = inner; }
    public String render() { return "_" + inner.render() + "_"; }
}

class UpperCaseDecorator implements Text {
    private final Text inner;
    UpperCaseDecorator(Text inner) { this.inner = inner; }
    public String render() { return inner.render().toUpperCase(); }
}
