import java.util.ArrayList;
import java.util.List;

// Provided — do not edit
class Executive implements Person {
    private final List<String> meetings = new ArrayList<>();

    public String meet(String visitorName) {
        meetings.add(visitorName);
        return "Executive is meeting " + visitorName;
    }

    public List<String> getMeetings() { return meetings; }
}
