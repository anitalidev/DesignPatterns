import java.util.ArrayList;
import java.util.List;

// Provided — do not edit
class UnauthorizedException extends RuntimeException {
    UnauthorizedException(String msg) { super(msg); }
}

// Provided — do not edit
interface Service {
    String getData();
    void deleteAll();
}

// Provided — do not edit
class AdminService implements Service {
    private final List<String> callLog = new ArrayList<>();

    public String getData() {
        callLog.add("getData");
        return "sensitive data";
    }

    public void deleteAll() {
        callLog.add("deleteAll");
    }

    public List<String> getCallLog() { return callLog; }
}

// TODO: implement the protection proxy
class SecureServiceProxy implements Service {
    // TODO: store the real service and the caller's role

    SecureServiceProxy(AdminService service, String role) {
        // TODO
    }

    @Override
    public String getData() {
        // TODO: allow only ADMIN, otherwise throw UnauthorizedException
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void deleteAll() {
        // TODO: allow only ADMIN, otherwise throw UnauthorizedException
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
