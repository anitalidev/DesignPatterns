import java.util.ArrayList;
import java.util.List;

interface Auditable {}

class Order implements Auditable {
    private final String id;
    Order(String id) { this.id = id; }
    public String getId() { return id; }
    public String toString() { return "Order(" + id + ")"; }
}

class Payment implements Auditable {
    private final double amount;
    Payment(double amount) { this.amount = amount; }
    public double getAmount() { return amount; }
    public String toString() { return "Payment(" + amount + ")"; }
}

class Product {
    private final String name;
    Product(String name) { this.name = name; }
    public String getName() { return name; }
    public String toString() { return "Product(" + name + ")"; }
}

class AuditLogger {
    private final List<String> entries = new ArrayList<>();

    public void log(Object obj) {
        if (obj instanceof Auditable) {
            entries.add(obj.toString());
        }
    }

    public List<String> getEntries() { return entries; }
}
