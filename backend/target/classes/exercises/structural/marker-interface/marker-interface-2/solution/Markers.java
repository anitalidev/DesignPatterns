import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

interface Auditable {}
interface Cacheable {}

class UserSession implements Auditable, Cacheable {
    private final String userId;
    UserSession(String userId) { this.userId = userId; }
    public String getUserId() { return userId; }
    public String toString() { return "UserSession(" + userId + ")"; }
}

class AuditEvent implements Auditable {
    private final String action;
    AuditEvent(String action) { this.action = action; }
    public String getAction() { return action; }
    public String toString() { return "AuditEvent(" + action + ")"; }
}

class StaticAsset implements Cacheable {
    private final String path;
    StaticAsset(String path) { this.path = path; }
    public String getPath() { return path; }
    public String toString() { return "StaticAsset(" + path + ")"; }
}

class TempData {
    private final String value;
    TempData(String value) { this.value = value; }
    public String getValue() { return value; }
    public String toString() { return "TempData(" + value + ")"; }
}

class AuditLogger {
    private final List<String> entries = new ArrayList<>();

    public void log(Object obj) {
        if (obj instanceof Auditable) entries.add(obj.toString());
    }

    public List<String> getEntries() { return entries; }
}

class ResponseCache {
    private final Map<String, Object> cache = new HashMap<>();

    public void store(String key, Object obj) {
        if (obj instanceof Cacheable) cache.put(key, obj);
    }

    public Object retrieve(String key) {
        return cache.get(key);
    }
}
