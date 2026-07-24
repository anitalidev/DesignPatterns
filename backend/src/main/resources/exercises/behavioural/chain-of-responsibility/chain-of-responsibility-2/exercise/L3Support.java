// TODO: implement L3Support — handles severity 3; throw if no next handler and severity > 3
class L3Support extends SupportHandler {

    @Override
    public String handle(Ticket ticket) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
