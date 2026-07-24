import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// TODO: define Auditable marker interface (no methods)

// TODO: define Cacheable marker interface (no methods)

// Classes — apply the correct markers:
//   UserSession  -> Auditable AND Cacheable
//   AuditEvent   -> Auditable only
//   StaticAsset  -> Cacheable only
//   TempData     -> neither

class UserSession {
    private final String userId;
    UserSession(String userId) { this.userId = userId; }
    public String getUserId() { return userId; }
    public String toString() { return "UserSession(" + userId + ")"; }
}

class AuditEvent {
    private final String action;
    AuditEvent(String action) { this.action = action; }
    public String getAction() { return action; }
    public String toString() { return "AuditEvent(" + action + ")"; }
}

class StaticAsset {
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

// TODO: complete AuditLogger — records only Auditable objects
class AuditLogger {
    private final List<String> entries = new ArrayList<>();

    public void log(Object obj) {
        // TODO
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<String> getEntries() { return entries; }
}

// TODO: complete ResponseCache — stores/retrieves only Cacheable objects
class ResponseCache {
    private final Map<String, Object> cache = new HashMap<>();

    public void store(String key, Object obj) {
        // TODO: store obj only if it is Cacheable
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Object retrieve(String key) {
        // TODO: return cached object or null if not present
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
