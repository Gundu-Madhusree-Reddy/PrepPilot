import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.StandardCopyOption;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class PrepPilot {

    private final List<Problem> problems = new ArrayList<>();

    private final Path dataFile;

    private int nextId = 1;

    private boolean idLimitReached = false;

    public PrepPilot() {
        this(Paths.get("data", "problems.txt"));
    }

    public PrepPilot(Path dataFile) {
        this.dataFile = Objects.requireNonNull(
            dataFile,
            "Data file path cannot be null."
        );

        loadProblems();
    }

    public void addProblem(String title, String topic,
                           String difficulty) {

        if (idLimitReached) {
            System.out.println(
                "Cannot add more problems. Maximum ID limit reached."
            );
            return;
        }

        Problem problem;

        try {
            problem = new Problem(nextId, title, topic, difficulty, "Unsolved");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }

        problems.add(problem);

        if (saveProblems()) {
             
            if (nextId == Integer.MAX_VALUE) {
                idLimitReached = true;
            } else {
                nextId++;
            }
            
            System.out.println("Problem added successfully!");
        
        } else {
            problems.remove(problem);
            System.out.println("Problem was not added because saving failed.");
        }
        
    }

    public void viewProblems() {

        if (problems.isEmpty()) {
            System.out.println("No problems found.");
            return;
        }

        System.out.println("\n--- All Problems ---");

        for (Problem problem : problems) {
            problem.display();
        }
    }

    public void updateStatus(int id, String newStatus) {

        for (Problem problem : problems) {
            
            if (problem.getId() == id) {
                
                String oldStatus = problem.getStatus();
                
                try {
                    problem.setStatus(newStatus);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                    return;
                }

                if (saveProblems()) {
                    System.out.println("Status updated successfully!");
                } else {
                    problem.setStatus(oldStatus);
                    System.out.println("Status update failed because saving failed.");
                }

                return;
            }
        }

        System.out.println("Problem ID not found.");
    }

    public void searchProblems(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            System.out.println("Search keyword cannot be blank.");
            return;
        }
        
        keyword = keyword.trim().toLowerCase(Locale.ROOT);
        
        boolean found = false;

        for (Problem problem : problems) {

            if (problem.getTitle().toLowerCase(Locale.ROOT)
                    .contains(keyword)
                    ||
                problem.getTopic().toLowerCase(Locale.ROOT)
                    .contains(keyword)) {

                problem.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching problems found.");
        }
    }

    public void filterByDifficulty(String difficulty) {

        try {
            difficulty = Problem.normalizeDifficulty(difficulty);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return;
        }
        
        boolean found = false;

        for (Problem problem : problems) {

            if (problem.getDifficulty()
                    .equals(difficulty)) {

                problem.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println(
                    "No matching problems found."
            );
        }
    }

    public void showStatistics() {

        int solved = 0;
        int unsolved = 0;
        int revision = 0;

        for (Problem problem : problems) {

            switch (problem.getStatus()) {

                case "Solved":
                    solved++;
                    break;

                case "Unsolved":
                    unsolved++;
                    break;

                case "Needs Revision":
                    revision++;
                    break;
            }
        }

        System.out.println("\n--- Preparation Statistics ---");
        System.out.println("Total problems: " + problems.size());
        System.out.println("Solved: " + solved);
        System.out.println("Unsolved: " + unsolved);
        System.out.println("Needs revision: " + revision);

        if (!problems.isEmpty()) {

            double percentage =
                    (solved * 100.0) / problems.size();

            System.out.printf(
                    "Completion percentage: %.2f%%%n",
                    percentage
            );
        }
    }

    private boolean saveProblems() {
        
        Path tempFile = null;
        
        try {
            
            Path absolutePath = dataFile.toAbsolutePath();
            Path parent = absolutePath.getParent();
            
            Files.createDirectories(parent);
            
            // Create a temporary file in the same directory.
            tempFile = Files.createTempFile(
                parent,
                "problems-",
                ".tmp"
            );
            
            // Write all records into the temporary file.
            try (BufferedWriter writer = Files.newBufferedWriter(
                tempFile,
                StandardCharsets.UTF_8)) {
                    
                    for (Problem problem : problems) {
                        writer.write(problem.toFileFormat());
                        writer.newLine();
                    }
                }
                
                // Replace the original file after successful writing.
                try {
                    Files.move(
                        tempFile,
                        absolutePath,
                        StandardCopyOption.ATOMIC_MOVE,
                        StandardCopyOption.REPLACE_EXISTING
                    );
                } catch (AtomicMoveNotSupportedException e) {
                    
                    Files.move(
                        tempFile,
                        absolutePath,
                        StandardCopyOption.REPLACE_EXISTING
                    );
                }
                
                return true;
            
            } catch (IOException e) {
                
                
                System.out.println(
                    "Could not save problems: " + e.getMessage()
                );
                
                return false;
            
            } finally {
                
                // Clean up the temporary file if it still exists.
                if (tempFile != null) {
                    try {
                        Files.deleteIfExists(tempFile);
                    } catch (IOException e) {
                        System.out.println(
                            "Could not delete temporary file: " + e.getMessage()
                        );
                    }
                }
            }
        }

    private void loadProblems() {

        if (!Files.exists(dataFile)) {
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(
                dataFile, StandardCharsets.UTF_8)) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("\\|", -1);

                if (parts.length != 5) {
                    continue;
                }

                try {
                    int id = Integer.parseInt(parts[0]);

                    Problem problem = new Problem(
                            id,
                            parts[1],
                            parts[2],
                            parts[3],
                            parts[4]
                    );

                    // Skip duplicate IDs.
                    if (containsId(id)) {
                        continue;
                    }

                    // Add the valid problem.
                    problems.add(problem);

                    // Protect ID generation from overflow.
                    if (id == Integer.MAX_VALUE) {
                        idLimitReached = true;
                    } else {
                        nextId = Math.max(nextId, id + 1);
                    }

                } catch (NumberFormatException e) {
                    System.out.println(
                            "Skipping a record with an invalid ID."
                    );
                } catch (IllegalArgumentException e) {
                    System.out.println("Skipping an invalid problem record.");
                }
            }

        } catch (IOException e) {
            System.out.println(
                    "Could not load problems: " + e.getMessage()
            );
        }
    }

    private boolean containsId(int id) {
        for (Problem problem : problems) {
            if (problem.getId() == id) {
                return true;
            }
        }
        return false;
    }
}
