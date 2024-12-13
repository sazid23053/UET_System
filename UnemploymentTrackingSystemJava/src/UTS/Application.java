package UTS;

class Application {
    private int applicationId;
    private int jobId;
    private String jobSeekerId;
    private String status;
    private String coverLetter;

    public Application(int applicationId, int jobId, String jobSeekerId, String status, String coverLetter) {
        this.applicationId = applicationId;
        this.jobId = jobId;
        this.jobSeekerId = jobSeekerId;
        this.status = status;
        this.coverLetter = coverLetter;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public int getJobId() {
        return jobId;
    }

    public String getJobSeekerId() {
        return jobSeekerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayApplicationDetails() {
        System.out.println("Application ID: " + applicationId);
        System.out.println("Job ID: " + jobId);
        System.out.println("Job Seeker ID: " + jobSeekerId);
        System.out.println("Status: " + status);
        System.out.println("Cover Letter: " + coverLetter);
    }

    @Override
    public String toString() {
        return applicationId + "," + jobId + "," + jobSeekerId + "," + status + "," + coverLetter;
    }

    public static Application fromString(String line) {
        String[] parts = line.split(",");
        return new Application(
            Integer.parseInt(parts[0]),
            Integer.parseInt(parts[1]),
            parts[2],
            parts[3],
            parts[4]
        );
    }
}
