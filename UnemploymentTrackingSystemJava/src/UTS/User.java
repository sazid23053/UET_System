package UTS;
abstract class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String password;

    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public abstract String getRole();

    @Override
    public String toString() {
        return "ID: " + userId + ", Name: " + name + ", Email: " + email + ", Role: " + getRole();
    }
}
