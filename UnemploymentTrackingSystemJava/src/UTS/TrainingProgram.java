package UTS;
class TrainingProgram {
    private int programId;
    private String programTitle;
    private String description;
    private String trainer;
    private String duration;
    private String location;

    public TrainingProgram(int programId, String programTitle, String description, String trainer, String duration, String location) {
        this.programId = programId;
        this.programTitle = programTitle;
        this.description = description;
        this.trainer = trainer;
        this.duration = duration;
        this.location = location;
    }

    public int getProgramId() {
        return programId;
    }

    public void displayProgramDetails() {
        System.out.println("Program ID: " + programId);
        System.out.println("Title: " + programTitle);
        System.out.println("Description: " + description);
        System.out.println("Trainer: " + trainer);
        System.out.println("Duration: " + duration);
        System.out.println("Location: " + location);
    }

    @Override
    public String toString() {
        return programId + "," + programTitle + "," + description + "," + trainer + "," + duration + "," + location;
    }

    public static TrainingProgram fromString(String line) {
        String[] parts = line.split(",");
        return new TrainingProgram(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3], parts[4], parts[5]);
    }
}

