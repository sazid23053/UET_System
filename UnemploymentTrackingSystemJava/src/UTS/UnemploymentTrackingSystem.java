package UTS;

import java.io.*;
import java.util.*;

public class UnemploymentTrackingSystem {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<User> users = new ArrayList<>();
    private static final List<JobPosting> jobList = new ArrayList<>();
    private static final List<TrainingProgram> trainingList = new ArrayList<>();
    private static final List<Application> applicationlist = new ArrayList<>();
    private static final String USERS_FILE = "users.txt";
    private static final String JOBS_FILE = "jobs.txt";
    private static final String TRAININGS_FILE = "trainings.txt";
    private static final String APPLICATIONS_FILE = "applications.txt";
    public static void main(String[] args) {
        ensureFileExists(USERS_FILE);
        ensureFileExists(JOBS_FILE);
        ensureFileExists(TRAININGS_FILE);
        ensureFileExists(APPLICATIONS_FILE);
        loadUsersFromFile();
        loadJobsFromFile();
        loadTrainingsFromFile();
        loadApplicationsFromFile();

        System.out.println("Welcome to the Unemployment Tracking System!");

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> {
                    saveUsersToFile();
                    saveJobsToFile();
                    saveTrainingsToFile();
                    saveApplicationsToFile();
                    System.out.println("Thank you for using the system!");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void ensureFileExists(String fileName) {
        File file = new File(fileName);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println(fileName + " file was not found. A new file has been created.");
            }
        } catch (IOException e) {
            System.out.println("Error creating file " + fileName + ": " + e.getMessage());
        }
    }

    private static void registerUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        if (isUserIdExists(userId)) {
            System.out.println("User ID already exists. Please try again.");
            return;
        }

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();
        System.out.print("Enter Role (Job Seeker, Employer, Admin): ");
        String role = scanner.nextLine();

        switch (role) {
            case "Job Seeker" -> users.add(new JobSeeker(userId, name, email, password));
            case "Employer" -> {
                System.out.print("Enter Company Name: ");
                String companyName = scanner.nextLine();
                users.add(new Employer(userId, name, email, password, companyName));
            }
            case "Admin" -> users.add(new Admin(userId, name, email, password));
            default -> {
                System.out.println("Invalid role. Registration failed.");
                return;
            }
        }
        System.out.println("Registration successful!");
    }

    private static boolean isUserIdExists(String userId) {
        return users.stream().anyMatch(user -> user.getUserId().equals(userId));
    }

    private static void loginUser() {
        System.out.print("Enter User ID: ");
        String userId = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getUserId().equals(userId) && user.password.equals(password)) {
                System.out.println("Login successful! Welcome, " + user.name + " (" + user.getRole() + ")");
                switch (user.getRole()) {
                    case "Job Seeker" -> jobSeekerMenu((JobSeeker) user);
                    case "Employer" -> employerMenu((Employer) user);
                    case "Admin" -> adminMenu((Admin) user);
                }
                return;
            }
        }
        System.out.println("Invalid credentials. Try again.");
    }

    private static void jobSeekerMenu(JobSeeker jobSeeker) {
        while (true) {
            System.out.println("\n1. Search Jobs");
            System.out.println("2. View Training Programs");
            System.out.println("3. Apply for a Job");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> searchJobs();
                case 2 -> viewTrainingPrograms();
                case 3 -> applyForJob();
                case 4 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void employerMenu(Employer employer) {
        while (true) {
            System.out.println("\n1. Post a Job");
            System.out.println("2. Post a Training Program");
            System.out.println("3. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> postJob(employer);
                case 2 -> postTrainingProgram(employer);
                case 3 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void adminMenu(Admin admin) {
        while (true) {
            System.out.println("\n1. View All Users");
            System.out.println("2. View All Jobs");
            System.out.println("3. View All Training Programs");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewAllUsers();
                case 2 -> viewAllJobs();
                case 3 -> viewAllTrainingPrograms();
                case 4 -> {
                    System.out.println("Logged out.");
                    return;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void searchJobs() {
        System.out.print("Enter search criteria (job title or skills): ");
        String criteria = scanner.nextLine().toLowerCase();
        for (JobPosting job : jobList) {
            if (job.getJobTitle().toLowerCase().contains(criteria) || job.toString().toLowerCase().contains(criteria)) {
                job.displayJobDetails();
                System.out.println("----------------------------");
            }
        }
    }

    private static void viewTrainingPrograms() {
        System.out.println("\nAvailable Training Programs:");
        for (TrainingProgram training : trainingList) {
            training.displayProgramDetails();
            System.out.println("----------------------------");
        }
    }

    private static void postJob(Employer employer) {
        System.out.print("Enter Job Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Job Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Required Skills: ");
        String skills = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();
        System.out.print("Enter Salary Range: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        JobPosting job = new JobPosting(generateID(), title, description, skills, location, salary, employer.getCompanyName());
        jobList.add(job);
        System.out.println("Job posted successfully!");
    }

    private static void postTrainingProgram(Employer employer) {
        System.out.print("Enter Program Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Program Description: ");
        String description = scanner.nextLine();
        System.out.print("Enter Trainer Name: ");
        String trainer = scanner.nextLine();
        System.out.print("Enter Program Duration: ");
        String duration = scanner.nextLine();
        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        TrainingProgram program = new TrainingProgram(generateID(), title, description, trainer, duration, location);
        trainingList.add(program);
        System.out.println("Training program posted successfully!");
    }

    private static int generateID() {
        return (int) (Math.random() * 1000);
    }

    private static void viewAllUsers() {
        System.out.println("\nAll Registered Users:");
        for (User user : users) {
            System.out.println(user);
        }
    }

    private static void viewAllJobs() {
        System.out.println("\nAll Job Postings:");
        for (JobPosting job : jobList) {
            job.displayJobDetails();
            System.out.println("----------------------------");
        }
    }

    private static void viewAllTrainingPrograms() {
        System.out.println("\nAll Training Programs:");
        for (TrainingProgram training : trainingList) {
            training.displayProgramDetails();
            System.out.println("----------------------------");
        }
    }
     private static void applyForJob() {
    System.out.print("Enter Job ID to apply: ");
    scanner.nextLine(); 
    System.out.print("Enter a cover letter (optional): ");
    System.out.println("Application submitted successfully!");
}


    private static void loadUsersFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String userId = parts[0];
                String name = parts[1];
                String email = parts[2];
                String password = parts[3];
                String role = parts[4];
                switch (role) {
                    case "Admin" -> users.add(new Admin(userId, name, email, password));
                    case "Job Seeker" -> users.add(new JobSeeker(userId, name, email, password));
                    case "Employer" -> users.add(new Employer(userId, name, email, password, parts[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    private static void saveUsersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.write(user.getUserId() + "," + user.name + "," + user.email + "," + user.password + "," + user.getRole());
                if (user instanceof Employer employer) {
                    writer.write("," + employer.getCompanyName());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }

    private static void loadJobsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(JOBS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jobList.add(JobPosting.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading jobs: " + e.getMessage());
        }
    }

    private static void saveJobsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(JOBS_FILE))) {
            for (JobPosting job : jobList) {
                writer.write(job.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving jobs: " + e.getMessage());
        }
    }

    private static void loadTrainingsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(TRAININGS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                trainingList.add(TrainingProgram.fromString(line));
            }
        } catch (IOException e) {
            System.out.println("Error loading training programs: " + e.getMessage());
        }
    }

    private static void saveTrainingsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(TRAININGS_FILE))) {
            for (TrainingProgram program : trainingList) {
                writer.write(program.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving training programs: " + e.getMessage());
        }
    }
  private static void loadApplicationsFromFile() {
    File file = new File(APPLICATIONS_FILE);
    if (!file.exists()) {
        try {
            file.createNewFile();
            System.out.println("File 'applications.txt' created.");
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return; // Exit the method if file creation fails
        }
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            applicationlist.add(Application.fromString(line));
        }
        System.out.println("Applications loaded successfully.");
    } catch (IOException e) {
        System.out.println("Error loading applications: " + e.getMessage());
    }
}
    private static void saveApplicationsToFile() {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(APPLICATIONS_FILE))) {
        for (Application app : applicationlist) {
            writer.write(app.toString());
            writer.newLine();
        }
        System.out.println("Applications saved successfully.");
    } catch (IOException e) {
        System.out.println("Error saving applications: " + e.getMessage());
    }
}


}
