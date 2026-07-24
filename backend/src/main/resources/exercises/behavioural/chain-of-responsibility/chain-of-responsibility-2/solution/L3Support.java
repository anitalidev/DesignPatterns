class L3Support extends SupportHandler {

    @Override
    public String handle(Ticket ticket) {
        if (ticket.getSeverity() == 3) return "L3 resolved: " + ticket.getIssue();
        if (next != null) return next.handle(ticket);
        throw new IllegalStateException("No handler for severity: " + ticket.getSeverity());
    }
}
