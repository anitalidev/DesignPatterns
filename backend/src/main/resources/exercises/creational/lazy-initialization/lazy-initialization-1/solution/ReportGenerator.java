class Report {
    final String content;
    Report(String content) { this.content = content; }
}

class ReportGenerator {
    private final String title;
    int computeCallCount = 0;

    private Report cached;

    ReportGenerator(String title) {
        this.title = title;
    }

    private Report compute() {
        computeCallCount++;
        return new Report("Report: " + title);
    }

    public Report getReport() {
        if (cached == null) {
            cached = compute();
        }
        return cached;
    }
}
