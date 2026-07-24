// Provided — do not edit
class Radio implements Device {
    private boolean on = false;
    private int volume = 30;

    public void powerOn() { on = true; }
    public void powerOff() { on = false; }
    public void setVolume(int v) { volume = Math.max(0, Math.min(100, v)); }
    public boolean isOn() { return on; }
    public int getVolume() { return volume; }
}
