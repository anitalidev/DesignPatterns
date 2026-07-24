import java.util.ArrayList;
import java.util.List;

// TODO: complete AuditLogger
class AuditLogger {
    private final List<String> entries = new ArrayList<>();

    public void log(Object obj) {
        // TODO: record obj.toString() only if obj is Auditable
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<String> getEntries() { return entries; }
}
