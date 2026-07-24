class Director extends ExpenseHandler {

    @Override
    public String approve(Expense expense) {
        if (expense.getAmount() <= 5000) return "Director approved: " + expense.getDescription();
        if (next != null) return next.approve(expense);
        throw new IllegalStateException("No handler for amount: " + expense.getAmount());
    }
}
