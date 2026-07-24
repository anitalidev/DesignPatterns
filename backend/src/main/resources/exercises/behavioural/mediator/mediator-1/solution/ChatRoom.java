import java.util.ArrayList;
import java.util.List;

class ChatRoom {
    private final List<User> users = new ArrayList<>();

    public void register(User user) {
        users.add(user);
    }

    public void broadcast(User sender, String message) {
        for (User user : users) {
            if (user != sender) user.receive(sender.getName(), message);
        }
    }
}
