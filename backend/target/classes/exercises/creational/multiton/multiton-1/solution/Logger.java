import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Logger {
    private static final Map<String, Logger> instances = new HashMap<>();

    private final String module;
    private final List<String> messages = new ArrayList<>();

    private Logger(String module) {
        this.module = module;
    }

    public static Logger getInstance(String module) {
        if (!instances.containsKey(module)) {
            instances.put(module, new Logger(module));
        }
        return instances.get(module);
    }

    public void log(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getModule() {
        return module;
    }
}
