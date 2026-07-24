class Manager extends ExpenseHandler {

    @Override
    public String approve(Expense expense) {
        if (expense.getAmount() <= 500) return "Manager approved: " + expense.getDescription();
        if (next != null) return next.approve(expense);
        throw new IllegalStateException("No handler for amount: " + expense.getAmount());
    }
}
