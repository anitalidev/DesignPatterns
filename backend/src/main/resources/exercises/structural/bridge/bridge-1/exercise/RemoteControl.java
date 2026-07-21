// Provided — do not edit
interface Device {
    void powerOn();
    void powerOff();
    void setVolume(int volume);
    boolean isOn();
    int getVolume();
}

// Provided — do not edit
class TV implements Device {
    private boolean on = false;
    private int volume = 10;

    public void powerOn()            { on = true; }
    public void powerOff()           { on = false; }
    public void setVolume(int v)     { volume = Math.max(0, Math.min(100, v)); }
    public boolean isOn()            { return on; }
    public int getVolume()           { return volume; }
}

// Provided — do not edit
class Radio implements Device {
    private boolean on = false;
    private int volume = 30;

    public void powerOn()            { on = true; }
    public void powerOff()           { on = false; }
    public void setVolume(int v)     { volume = Math.max(0, Math.min(100, v)); }
    public boolean isOn()            { return on; }
    public int getVolume()           { return volume; }
}

// TODO: implement RemoteControl
class RemoteControl {
    // TODO: store the Device

    RemoteControl(Device device) {
        // TODO: store device
    }

    public void togglePower() {
        // TODO: turn on if off, turn off if on
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void setVolume(int volume) {
        // TODO: delegate to device
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public Device getDevice() {
        // TODO: return the device
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// TODO: extend RemoteControl with a mute shortcut
class AdvancedRemote extends RemoteControl {
    AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        // TODO: set volume to 0
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
