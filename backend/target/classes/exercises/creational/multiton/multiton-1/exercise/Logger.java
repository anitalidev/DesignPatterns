import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Logger {
    // TODO: add a static map to hold one Logger per module name

    private final String module;

    // TODO: add a list to hold this logger's messages

    private Logger(String module) {
        this.module = module;
        // TODO: initialise the message list
    }

    public static Logger getInstance(String module) {
        // TODO: return the existing Logger for this module, or create and store a new one
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void log(String message) {
        // TODO: append message to this logger's list
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<String> getMessages() {
        // TODO: return the message list
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public String getModule() {
        return module;
    }
}
