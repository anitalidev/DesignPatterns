import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Provided — the interface your app already uses. Do not edit.
interface Tracker {
    void track(String event, Map<String, String> data);
}

// Provided — the vendor SDK with an incompatible API. Do not edit.
class Analytics {
    private final List<String> recorded = new ArrayList<>();

    public void recordEvent(String name, Map<String, String> properties) {
        recorded.add(name + ":" + properties);
    }

    public List<String> getRecorded() { return recorded; }
}

// TODO: implement the adapter
class AnalyticsAdapter implements Tracker {
    // TODO: hold a reference to Analytics

    AnalyticsAdapter(Analytics analytics) {
        // TODO: store the analytics instance
    }

    @Override
    public void track(String event, Map<String, String> data) {
        // TODO: delegate to analytics.recordEvent()
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
