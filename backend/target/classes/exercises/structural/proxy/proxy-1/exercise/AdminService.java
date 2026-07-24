import java.util.ArrayList;
import java.util.List;

// Provided — do not edit
class AdminService implements Service {
    private final List<String> callLog = new ArrayList<>();

    public String getData() {
        callLog.add("getData");
        return "sensitive data";
    }

    public void deleteAll() {
        callLog.add("deleteAll");
    }

    public List<String> getCallLog() { return callLog; }
}
