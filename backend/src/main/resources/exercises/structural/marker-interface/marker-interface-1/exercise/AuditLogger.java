import java.util.ArrayList;
import java.util.List;

// TODO: define the Auditable marker interface (no methods)

// TODO: implement Auditable on Order and Payment, but NOT on Product
class Order {
    private final String id;
    Order(String id) { this.id = id; }
    public String getId() { return id; }
}

class Payment {
    private final double amount;
    Payment(double amount) { this.amount = amount; }
    public double getAmount() { return amount; }
}

class Product {
    private final String name;
    Product(String name) { this.name = name; }
    public String getName() { return name; }
}

// TODO: complete AuditLogger
class AuditLogger {
    private final List<String> entries = new ArrayList<>();

    public void log(Object obj) {
        // TODO: record obj.toString() only if obj is Auditable
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public List<String> getEntries() { return entries; }
}
