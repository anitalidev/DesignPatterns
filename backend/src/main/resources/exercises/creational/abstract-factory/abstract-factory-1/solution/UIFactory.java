interface Button {
    String render();
}

interface Checkbox {
    String render();
}

class MacButton implements Button {
    public String render() { return "MacButton"; }
}

class MacCheckbox implements Checkbox {
    public String render() { return "MacCheckbox"; }
}

class WindowsButton implements Button {
    public String render() { return "WindowsButton"; }
}

class WindowsCheckbox implements Checkbox {
    public String render() { return "WindowsCheckbox"; }
}

interface UIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

class MacUIFactory implements UIFactory {
    public Button createButton()     { return new MacButton(); }
    public Checkbox createCheckbox() { return new MacCheckbox(); }
}

class WindowsUIFactory implements UIFactory {
    public Button createButton()     { return new WindowsButton(); }
    public Checkbox createCheckbox() { return new WindowsCheckbox(); }
}

class Application {
    private UIFactory factory;

    Application(UIFactory factory) {
        this.factory = factory;
    }

    Button buildButton() {
        return factory.createButton();
    }

    Checkbox buildCheckbox() {
        return factory.createCheckbox();
    }
}
