package UTS;
class Admin extends User {
    public Admin(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    @Override
    public String getRole() {
        return "Admin";
    }
}
