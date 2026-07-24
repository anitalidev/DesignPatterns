// TODO: produce comma-separated output
// Assume that for this exercise, column names are name, age, city in that order
class CSVExporter extends DataExporter {

    // EFFECT: return "name,age,city\n"
    protected String formatHeader() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // e.g. GIVEN:  row = ["Alice", "30", "London"]
    //      EFFECT: return "Alice,30,London\n"
    protected String formatRow(String[] row) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // EFFECT: return ""
    protected String formatFooter() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
