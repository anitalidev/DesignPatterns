class L2Support extends SupportHandler {

    @Override
    public String handle(Ticket ticket) {
        if (ticket.getSeverity() == 2) return "L2 resolved: " + ticket.getIssue();
        if (next != null) return next.handle(ticket);
        throw new IllegalStateException("No handler for severity: " + ticket.getSeverity());
    }
}
