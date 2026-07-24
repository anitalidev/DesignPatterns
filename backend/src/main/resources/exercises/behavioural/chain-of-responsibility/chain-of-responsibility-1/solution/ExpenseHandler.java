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

abstract class ExpenseHandler {
    protected ExpenseHandler next;

    public ExpenseHandler setNext(ExpenseHandler next) {
        this.next = next;
        return next;
    }

    public abstract String approve(Expense expense);
}

class Manager extends ExpenseHandler {
    @Override
    public String approve(Expense expense) {
        if (expense.getAmount() <= 500) return "Manager approved: " + expense.getDescription();
        if (next != null) return next.approve(expense);
        throw new IllegalStateException("No handler for amount: " + expense.getAmount());
    }
}

class Director extends ExpenseHandler {
    @Override
    public String approve(Expense expense) {
        if (expense.getAmount() <= 5000) return "Director approved: " + expense.getDescription();
        if (next != null) return next.approve(expense);
        throw new IllegalStateException("No handler for amount: " + expense.getAmount());
    }
}

class CEO extends ExpenseHandler {
    @Override
    public String approve(Expense expense) {
        return "CEO approved: " + expense.getDescription();
    }
}
