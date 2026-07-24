abstract class ExpenseHandler {
    protected ExpenseHandler next;

    public ExpenseHandler setNext(ExpenseHandler next) {
        this.next = next;
        return next;
    }

    public abstract String approve(Expense expense);
}
