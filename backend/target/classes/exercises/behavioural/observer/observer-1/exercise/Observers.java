class Logger {
    String lastLog;
    void log(String state) { this.lastLog = state; }
}

class UI {
    String rendered;
    void render(String state) { this.rendered = state; }
}
