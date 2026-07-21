import java.util.List;

abstract class DataExporter {
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

class CSVExporter extends DataExporter {
    protected String formatHeader(List<String[]> rows) { return "name,age,city\n"; }
    protected String formatRow(String[] row)           { return String.join(",", row) + "\n"; }
    protected String formatFooter()                    { return ""; }
}

class JSONExporter extends DataExporter {
    protected String formatHeader(List<String[]> rows) { return "[\n"; }
    protected String formatRow(String[] row) {
        return "  {\"name\":\"" + row[0] + "\",\"age\":\"" + row[1] + "\",\"city\":\"" + row[2] + "\"},\n";
    }
    protected String formatFooter() { return "]\n"; }
}
