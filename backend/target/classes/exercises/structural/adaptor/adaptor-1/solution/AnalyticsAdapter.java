import java.util.ArrayList;
import java.util.List;
import java.util.Map;

interface Tracker {
    void track(String event, Map<String, String> data);
}

class Analytics {
    private final List<String> recorded = new ArrayList<>();

    public void recordEvent(String name, Map<String, String> properties) {
        recorded.add(name + ":" + properties);
    }

    public List<String> getRecorded() { return recorded; }
}

class AnalyticsAdapter implements Tracker {
    private final Analytics analytics;

    AnalyticsAdapter(Analytics analytics) {
        this.analytics = analytics;
    }

    @Override
    public void track(String event, Map<String, String> data) {
        analytics.recordEvent(event, data);
    }
}
