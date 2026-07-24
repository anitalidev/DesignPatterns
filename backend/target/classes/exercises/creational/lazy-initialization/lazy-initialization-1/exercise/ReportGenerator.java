class ReportGenerator {
    private final String title;
    int computeCallCount = 0; // visible for testing

    // TODO: add a field to cache the report

    ReportGenerator(String title) {
        this.title = title;
    }

    // Simulates a costly operation — must be called at most once per instance
    private Report compute() {
        computeCallCount++;
        return new Report("Report: " + title);
    }

    public Report getReport() {
        // TODO: return the cached report, computing it only on the first call
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
