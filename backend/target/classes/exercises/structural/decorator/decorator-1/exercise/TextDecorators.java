// Provided — do not edit
interface Text {
    String render();
}

// Provided — do not edit
class PlainText implements Text {
    private final String content;
    PlainText(String content) { this.content = content; }
    public String render()   { return content; }
}

// TODO: implement BoldDecorator
// render() should return "**" + inner.render() + "**"
class BoldDecorator implements Text {
    BoldDecorator(Text inner) {
        // TODO
    }

    public String render() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement ItalicDecorator
// render() should return "_" + inner.render() + "_"
class ItalicDecorator implements Text {
    ItalicDecorator(Text inner) {
        // TODO
    }

    public String render() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement UpperCaseDecorator
// render() should return inner.render().toUpperCase()
class UpperCaseDecorator implements Text {
    UpperCaseDecorator(Text inner) {
        // TODO
    }

    public String render() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
