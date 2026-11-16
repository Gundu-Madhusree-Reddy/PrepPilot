import java.nio.file.Files;
import java.nio.file.Path;

public class DataSaveFailureTest {

    public static void main(String[] args) throws Exception {

        Path directory =
                Files.createTempDirectory("preppilot-save-failure");

        // Create a regular file where a directory is expected.
        Path blocker = directory.resolve("blocker");
        Files.writeString(blocker, "This is a file.");

        // Saving here should fail because the parent is not a directory.
        Path invalidPath = blocker.resolve("problems.txt");

        PrepPilot tracker = new PrepPilot(invalidPath);

        tracker.addProblem(
                "Two Sum",
                "Arrays",
                "Easy"
        );

        System.out.println("\nChecking problems after save failure:");

        tracker.viewProblems();

        tracker.showStatistics();
    }
}