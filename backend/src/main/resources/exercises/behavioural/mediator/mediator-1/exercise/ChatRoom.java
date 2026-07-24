import java.util.ArrayList;
import java.util.List;

// TODO: implement ChatRoom
class ChatRoom {
    // TODO: keep a list of users

    public void register(User user) {
        // TODO: add user to the list
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void broadcast(User sender, String message) {
        // TODO: deliver message to all users except the sender
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

// Provided — do not edit the constructor or receive(); complete send()
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
        // TODO: broadcast via chatRoom
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void receive(String from, String message) {
        inbox.add(from + ": " + message);
    }

    public String getName() { return name; }
    public List<String> getInbox() { return inbox; }
}
