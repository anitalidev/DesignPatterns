// Provided — do not edit these component interfaces or classes

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
