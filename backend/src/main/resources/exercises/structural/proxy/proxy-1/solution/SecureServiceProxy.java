import java.util.ArrayList;
import java.util.List;

class UnauthorizedException extends RuntimeException {
    UnauthorizedException(String msg) { super(msg); }
}

interface Service {
    String getData();
    void deleteAll();
}

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

class SecureServiceProxy implements Service {
    private final AdminService service;
    private final String role;

    SecureServiceProxy(AdminService service, String role) {
        this.service = service;
        this.role    = role;
    }

    private void checkAccess() {
        if (!"ADMIN".equals(role)) throw new UnauthorizedException("Access denied for role: " + role);
    }

    @Override
    public String getData() {
        checkAccess();
        return service.getData();
    }

    @Override
    public void deleteAll() {
        checkAccess();
        service.deleteAll();
    }
}
