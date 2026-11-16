
import java.nio.file.Files;
import java.nio.file.Path;

public class IdOverflowBoundaryTest {

    public static void main(String[] args) throws Exception {

        Path directory = Files.createTempDirectory("preppilot-boundary");
        Path file = directory.resolve("problems.txt");

        // Start with the second-highest possible ID.
        Files.writeString(
            file,
            "2147483646|Last Available ID|Arrays|Easy|Solved\n"
        );

        PrepPilot tracker = new PrepPilot(file);

        System.out.println("Adding the final available ID:");
        tracker.addProblem("Binary Search", "Searching", "Medium");

        System.out.println("\nProblems after adding:");
        tracker.viewProblems();

        System.out.println("\nAttempting one more addition:");
        tracker.addProblem("Two Sum", "Arrays", "Easy");

        String savedData = Files.readString(file);

        if (!savedData.contains("2147483647|Binary Search")) {
            throw new AssertionError("Maximum ID was not saved correctly.");
        }

        if (savedData.contains("Two Sum")) {
            throw new AssertionError("An extra problem was incorrectly added.");
        }

        System.out.println("\nID boundary test passed!");
    }
}
