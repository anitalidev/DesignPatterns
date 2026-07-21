import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Provided — do not edit
interface Person {
    String meet(String visitorName);
}

// Provided — do not edit
class Executive implements Person {
    private final List<String> meetings = new ArrayList<>();

    public String meet(String visitorName) {
        meetings.add(visitorName);
        return "Executive is meeting " + visitorName;
    }

    public List<String> getMeetings() { return meetings; }
}

// TODO: implement the receptionist proxy
class ReceptionistProxy implements Person {
    // TODO: store the executive and the appointment list

    ReceptionistProxy(Executive executive) {
        // TODO
    }

    public void addAppointment(String visitorName) {
        // TODO: add visitorName to the approved list
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String meet(String visitorName) {
        // TODO: delegate to executive if approved, otherwise return a refusal message
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
