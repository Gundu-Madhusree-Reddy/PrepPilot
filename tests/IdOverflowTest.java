import java.nio.file.Files;
import java.nio.file.Path;

public class IdOverflowTest {

    public static void main(String[] args) throws Exception {

        Path directory =
                Files.createTempDirectory("preppilot-overflow-test");

        Path file = directory.resolve("problems.txt");

        // Store a problem with the maximum possible int ID.
        String data =
                "2147483647|Maximum ID Problem|Arrays|Easy|Solved\n";

        Files.writeString(file, data);

        PrepPilot tracker = new PrepPilot(file);

        System.out.println("Loaded existing problem:");
        tracker.viewProblems();

        System.out.println("\nAttempting to add another problem:");

        tracker.addProblem(
                "Binary Search",
                "Searching",
                "Medium"
        );

        System.out.println("\nFinal statistics:");
        tracker.showStatistics();
    }
}