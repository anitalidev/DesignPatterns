class CEO extends ExpenseHandler {

    @Override
    public String approve(Expense expense) {
        return "CEO approved: " + expense.getDescription();
    }
}
