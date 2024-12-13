package UTS;
class Employer extends User {
    private String companyName;

    public Employer(String userId, String name, String email, String password, String companyName) {
        super(userId, name, email, password);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getRole() {
        return "Employer";
    }

    @Override
    public String toString() {
        return super.toString() + ", Company: " + companyName;
    }
}
