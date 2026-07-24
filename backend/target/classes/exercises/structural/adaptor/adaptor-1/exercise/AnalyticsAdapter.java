import java.util.Map;

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
