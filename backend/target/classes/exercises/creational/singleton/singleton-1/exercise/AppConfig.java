class AppConfig {
    private String theme = "light";
    private String language = "en";

    public AppConfig() {}

    public void set(String key, String value) {
        switch (key) {
            case "theme":    theme = value; break;
            case "language": language = value; break;
        }
    }

    public String get(String key) {
        switch (key) {
            case "theme":    return theme;
            case "language": return language;
            default:         return null;
        }
    }
}
