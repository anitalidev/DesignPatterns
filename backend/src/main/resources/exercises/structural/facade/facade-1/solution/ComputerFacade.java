import java.util.ArrayList;
import java.util.List;

class CPU {
    private final List<String> log;
    CPU(List<String> log) { this.log = log; }
    public void initialize() { log.add("CPU.initialize"); }
}

class Memory {
    private final List<String> log;
    Memory(List<String> log) { this.log = log; }
    public void load() { log.add("Memory.load"); }
}

class HardDrive {
    private final List<String> log;
    HardDrive(List<String> log) { this.log = log; }
    public void spin() { log.add("HardDrive.spin"); }
}

class ComputerFacade {
    private final CPU cpu;
    private final Memory memory;
    private final HardDrive hardDrive;

    ComputerFacade(CPU cpu, Memory memory, HardDrive hardDrive) {
        this.cpu       = cpu;
        this.memory    = memory;
        this.hardDrive = hardDrive;
    }

    public void pressStart() {
        cpu.initialize();
        memory.load();
        hardDrive.spin();
    }
}
