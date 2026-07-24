class JSONExporter extends DataExporter {
    protected String formatHeader() { return "[\n"; }
    protected String formatRow(String[] row) {
        return "  {\"name\":\"" + row[0] + "\",\"age\":\"" + row[1] + "\",\"city\":\"" + row[2] + "\"},\n";
    }

    protected String formatFooter() { return "]\n"; }
}
