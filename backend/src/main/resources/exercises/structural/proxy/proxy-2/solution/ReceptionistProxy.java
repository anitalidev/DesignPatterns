import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

interface Person {
    String meet(String visitorName);
}

class Executive implements Person {
    private final List<String> meetings = new ArrayList<>();

    public String meet(String visitorName) {
        meetings.add(visitorName);
        return "Executive is meeting " + visitorName;
    }

    public List<String> getMeetings() { return meetings; }
}

class ReceptionistProxy implements Person {
    private final Executive executive;
    private final Set<String> appointments = new HashSet<>();

    ReceptionistProxy(Executive executive) {
        this.executive = executive;
    }

    public void addAppointment(String visitorName) {
        appointments.add(visitorName);
    }

    @Override
    public String meet(String visitorName) {
        if (appointments.contains(visitorName)) {
            return executive.meet(visitorName);
        }
        return visitorName + " does not have an appointment";
    }
}
