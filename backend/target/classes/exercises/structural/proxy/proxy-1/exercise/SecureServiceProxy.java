// TODO: implement the protection proxy
// Roles:
//   ADMIN — full access: getData() and deleteAll()
//   USER  — read-only:  getData() only; deleteAll() throws UnauthorizedException
//   other — no access:  both methods throw UnauthorizedException
class SecureServiceProxy implements Service {
    // TODO: store the real service and the caller's role

    SecureServiceProxy(AdminService service, String role) {
        // TODO
    }

    @Override
    public String getData() {
        // TODO: allow ADMIN and USER; throw UnauthorizedException for any other role
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void deleteAll() {
        // TODO: allow ADMIN only; throw UnauthorizedException for USER and any other role
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
