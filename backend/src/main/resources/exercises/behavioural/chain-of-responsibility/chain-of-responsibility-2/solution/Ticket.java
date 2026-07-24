class Ticket {
    private final String issue;
    private final int severity;

    Ticket(String issue, int severity) {
        this.issue    = issue;
        this.severity = severity;
    }

    public String getIssue() {
        return issue;
    }

    public int getSeverity() {
        return severity;
    }
}
