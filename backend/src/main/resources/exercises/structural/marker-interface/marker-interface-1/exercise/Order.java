// TODO: implement the Auditable marker interface on Order
class Order {
    private final String id;
    Order(String id) { this.id = id; }
    public String getId() { return id; }
}
