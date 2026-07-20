class AppConfig {
    private static AppConfig instance;

    private String theme = "light";
    private String language = "en";

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (instance == null) instance = new AppConfig();
        return instance;
    }

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
