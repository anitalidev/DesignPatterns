import java.util.List;

// Provided — do not edit
abstract class DataExporter {
    // Template method — defines the algorithm; do not override
    public final String export(List<String[]> rows) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatHeader(rows));
        for (String[] row : rows) sb.append(formatRow(row));
        sb.append(formatFooter());
        return sb.toString();
    }

    protected abstract String formatHeader(List<String[]> rows);
    protected abstract String formatRow(String[] row);
    protected abstract String formatFooter();
}

// TODO: produce comma-separated output
// Header: "name,age,city\n"  (column names joined by commas)
// Row:    "Alice,30,London\n"
// Footer: "" (empty)
class CSVExporter extends DataExporter {
    protected String formatHeader(List<String[]> rows) { throw new UnsupportedOperationException("Not yet implemented"); }
    protected String formatRow(String[] row)           { throw new UnsupportedOperationException("Not yet implemented"); }
    protected String formatFooter()                    { throw new UnsupportedOperationException("Not yet implemented"); }
}

// TODO: produce JSON array output
// Header: "[\n"
// Row:    "  {\"name\":\"Alice\",\"age\":\"30\",\"city\":\"London\"},\n"
// Footer: "]\n"
// (column names are name, age, city in that order)
class JSONExporter extends DataExporter {
    protected String formatHeader(List<String[]> rows) { throw new UnsupportedOperationException("Not yet implemented"); }
    protected String formatRow(String[] row)           { throw new UnsupportedOperationException("Not yet implemented"); }
    protected String formatFooter()                    { throw new UnsupportedOperationException("Not yet implemented"); }
}
