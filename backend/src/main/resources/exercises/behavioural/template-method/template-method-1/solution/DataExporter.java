import java.util.List;

abstract class DataExporter {
    public final String export(List<String[]> rows) {
        StringBuilder sb = new StringBuilder();
        sb.append(formatHeader());
        for (String[] row : rows) sb.append(formatRow(row));
        sb.append(formatFooter());
        return sb.toString();
    }

    protected abstract String formatHeader();
    protected abstract String formatRow(String[] row);
    protected abstract String formatFooter();
}
