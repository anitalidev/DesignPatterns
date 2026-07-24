import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Provided — the vendor SDK with an incompatible API. Do not edit.
class Analytics {
    private final List<String> recorded = new ArrayList<>();

    public void recordEvent(String name, Map<String, String> properties) {
        recorded.add(name + ":" + properties);
    }

    public List<String> getRecorded() { return recorded; }
}
