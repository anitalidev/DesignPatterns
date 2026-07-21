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

// -------------------------------------------------------
// TODO: define the UIFactory interface with:
//   Button    createButton()
//   Checkbox  createCheckbox()

// TODO: implement MacUIFactory (returns Mac components)

// TODO: implement WindowsUIFactory (returns Windows components)

// TODO: complete Application so it uses the factory it receives
class Application {
    // Store the factory here

    Application(Object factory) {
        // TODO: store the factory
    }

    Button buildButton() {
        // TODO: use the factory
        throw new UnsupportedOperationException("Not yet implemented");
    }

    Checkbox buildCheckbox() {
        // TODO: use the factory
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
