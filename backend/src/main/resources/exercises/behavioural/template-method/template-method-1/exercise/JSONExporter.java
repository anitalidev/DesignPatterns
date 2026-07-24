// TODO: produce JSON array output
// Assume that for this exercise, column names are name, age, city in that order
class JSONExporter extends DataExporter {

    // EFFECT: return "[\n"
    protected String formatHeader() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // e.g. GIVEN:  row = ["Alice", "30", "London"]
    //      EFFECT: return "  {\"name\":\"Alice\",\"age\":\"30\",\"city\":\"London\"},\n"
    protected String formatRow(String[] row) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    // EFFECT: return "]\n"
    protected String formatFooter() {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
