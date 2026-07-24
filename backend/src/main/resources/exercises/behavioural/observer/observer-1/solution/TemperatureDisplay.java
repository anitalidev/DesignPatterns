class TemperatureDisplay implements Observer {
    private double latest;
    private int count;

    public void update(double temperature) { this.latest = temperature; count++; }
    public double getLatest() { return latest; }
    public int getReadingCount() { return count; }
}
