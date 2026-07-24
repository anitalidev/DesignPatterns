class CSVExporter extends DataExporter {
    protected String formatHeader() { return "name,age,city\n"; }
    protected String formatRow(String[] row) { return String.join(",", row) + "\n"; }
    protected String formatFooter() { return ""; }
}
