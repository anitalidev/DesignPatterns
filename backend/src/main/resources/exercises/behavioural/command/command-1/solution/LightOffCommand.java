class LightOffCommand implements Command {
    private final Light light;
    LightOffCommand(Light light) { this.light = light; }
    public void execute() { light.turnOff(); }
    public void undo() { light.turnOn(); }
}
