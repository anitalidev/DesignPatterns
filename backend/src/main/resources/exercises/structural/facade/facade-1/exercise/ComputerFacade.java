import java.util.ArrayList;
import java.util.List;

// Provided — do not edit
class CPU {
    private final List<String> log;
    CPU(List<String> log) { this.log = log; }
    public void initialize() { log.add("CPU.initialize"); }
}

// Provided — do not edit
class Memory {
    private final List<String> log;
    Memory(List<String> log) { this.log = log; }
    public void load() { log.add("Memory.load"); }
}

// Provided — do not edit
class HardDrive {
    private final List<String> log;
    HardDrive(List<String> log) { this.log = log; }
    public void spin() { log.add("HardDrive.spin"); }
}

// TODO: implement the facade
class ComputerFacade {
    // TODO: hold references to CPU, Memory, and HardDrive

    ComputerFacade(CPU cpu, Memory memory, HardDrive hardDrive) {
        // TODO: store the subsystems
    }

    public void pressStart() {
        // TODO: call each subsystem in the correct order
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
