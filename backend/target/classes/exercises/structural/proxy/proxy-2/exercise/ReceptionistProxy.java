import java.util.HashSet;
import java.util.Set;

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
