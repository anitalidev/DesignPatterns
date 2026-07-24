// Provided — do not edit
class Expense {
    private final String description;
    private final double amount;
    Expense(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public String getDescription() { return description; }
    public double getAmount() { return amount; }
}

// Provided — do not edit
abstract class ExpenseHandler {
    protected ExpenseHandler next;

    public ExpenseHandler setNext(ExpenseHandler next) {
        this.next = next;
        return next;
    }

    public abstract String approve(Expense expense);
}

// TODO: implement Manager — approves up to $500
class Manager extends ExpenseHandler {
    @Override
    public String approve(Expense expense) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement Director — approves up to $5000
class Director extends ExpenseHandler {
    @Override
    public String approve(Expense expense) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: implement CEO — approves any amount
class CEO extends ExpenseHandler {
    @Override
    public String approve(Expense expense) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
