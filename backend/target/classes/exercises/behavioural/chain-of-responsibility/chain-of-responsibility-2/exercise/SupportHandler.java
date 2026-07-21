// Provided — do not edit
class Ticket {
    private final String issue;
    private final int severity;
    Ticket(String issue, int severity) {
        this.issue    = issue;
        this.severity = severity;
    }
    public String getIssue()    { return issue; }
    public int    getSeverity() { return severity; }
}

// Provided — do not edit
abstract class SupportHandler {
    protected SupportHandler next;

    public SupportHandler setNext(SupportHandler next) {
        this.next = next;
        return next;
    }

    public abstract String handle(Ticket ticket);
}

// TODO: implement L1Support — handles severity 1
class L1Support extends SupportHandler {
    @Override
    public String handle(Ticket ticket) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement L2Support — handles severity 2
class L2Support extends SupportHandler {
    @Override
    public String handle(Ticket ticket) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement L3Support — handles severity 3; throw if no next handler and severity > 3
class L3Support extends SupportHandler {
    @Override
    public String handle(Ticket ticket) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
