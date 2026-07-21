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

abstract class SupportHandler {
    protected SupportHandler next;

    public SupportHandler setNext(SupportHandler next) {
        this.next = next;
        return next;
    }

    public abstract String handle(Ticket ticket);
}

class L1Support extends SupportHandler {
    @Override
    public String handle(Ticket ticket) {
        if (ticket.getSeverity() == 1) return "L1 resolved: " + ticket.getIssue();
        if (next != null) return next.handle(ticket);
        throw new IllegalStateException("No handler for severity: " + ticket.getSeverity());
    }
}

class L2Support extends SupportHandler {
    @Override
    public String handle(Ticket ticket) {
        if (ticket.getSeverity() == 2) return "L2 resolved: " + ticket.getIssue();
        if (next != null) return next.handle(ticket);
        throw new IllegalStateException("No handler for severity: " + ticket.getSeverity());
    }
}

class L3Support extends SupportHandler {
    @Override
    public String handle(Ticket ticket) {
        if (ticket.getSeverity() == 3) return "L3 resolved: " + ticket.getIssue();
        if (next != null) return next.handle(ticket);
        throw new IllegalStateException("No handler for severity: " + ticket.getSeverity());
    }
}
