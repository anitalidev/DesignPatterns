interface Device {
    void powerOn();
    void powerOff();
    void setVolume(int volume);
    boolean isOn();
    int getVolume();
}

class TV implements Device {
    private boolean on = false;
    private int volume = 10;

    public void powerOn()        { on = true; }
    public void powerOff()       { on = false; }
    public void setVolume(int v) { volume = Math.max(0, Math.min(100, v)); }
    public boolean isOn()        { return on; }
    public int getVolume()       { return volume; }
}

class Radio implements Device {
    private boolean on = false;
    private int volume = 30;

    public void powerOn()        { on = true; }
    public void powerOff()       { on = false; }
    public void setVolume(int v) { volume = Math.max(0, Math.min(100, v)); }
    public boolean isOn()        { return on; }
    public int getVolume()       { return volume; }
}

class RemoteControl {
    private final Device device;

    RemoteControl(Device device) {
        this.device = device;
    }

    public void togglePower() {
        if (device.isOn()) device.powerOff();
        else               device.powerOn();
    }

    public void setVolume(int volume) {
        device.setVolume(volume);
    }

    public Device getDevice() {
        return device;
    }
}

class AdvancedRemote extends RemoteControl {
    AdvancedRemote(Device device) {
        super(device);
    }

    public void mute() {
        setVolume(0);
    }
}
