package UTS;
class JobSeeker extends User {
    public JobSeeker(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    @Override
    public String getRole() {
        return "Job Seeker";
    }
}