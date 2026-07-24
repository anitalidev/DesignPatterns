import java.util.ArrayList;
import java.util.List;

class User {
    private final String name;
    private final ChatRoom chatRoom;
    private final List<String> inbox = new ArrayList<>();

    User(String name, ChatRoom chatRoom) {
        this.name     = name;
        this.chatRoom = chatRoom;
        chatRoom.register(this);
    }

    public void send(String message) {
        chatRoom.broadcast(this, message);
    }

    public void receive(String from, String message) {
        inbox.add(from + ": " + message);
    }

    public String getName() {
        return name;
    }

    public List<String> getInbox() {
        return inbox;
    }
}
