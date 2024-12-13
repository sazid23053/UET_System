package UTS;
class JobPosting {
    private int jobId;
    private String jobTitle;
    private String jobDescription;
    private String requiredSkills;
    private String location;
    private double salaryRange;
    private String employerName;

    public JobPosting(int jobId, String jobTitle, String jobDescription, String requiredSkills, String location, double salaryRange, String employerName) {
        this.jobId = jobId;
        this.jobTitle = jobTitle;
        this.jobDescription = jobDescription;
        this.requiredSkills = requiredSkills;
        this.location = location;
        this.salaryRange = salaryRange;
        this.employerName = employerName;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void displayJobDetails() {
        System.out.println("Job ID: " + jobId);
        System.out.println("Title: " + jobTitle);
        System.out.println("Description: " + jobDescription);
        System.out.println("Required Skills: " + requiredSkills);
        System.out.println("Location: " + location);
        System.out.println("Salary Range: $" + salaryRange);
        System.out.println("Posted By: " + employerName);
    }

    @Override
    public String toString() {
        return jobId + "," + jobTitle + "," + jobDescription + "," + requiredSkills + "," + location + "," + salaryRange + "," + employerName;
    }

    public static JobPosting fromString(String line) {
        String[] parts = line.split(",");
        return new JobPosting(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], Double.parseDouble(parts[5]), parts[6]);
    }
}
