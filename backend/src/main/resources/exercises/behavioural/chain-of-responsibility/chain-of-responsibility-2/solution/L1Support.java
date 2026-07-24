class L1Support extends SupportHandler {

    @Override
    public String handle(Ticket ticket) {
        if (ticket.getSeverity() == 1) return "L1 resolved: " + ticket.getIssue();
        if (next != null) return next.handle(ticket);
        throw new IllegalStateException("No handler for severity: " + ticket.getSeverity());
    }
}
