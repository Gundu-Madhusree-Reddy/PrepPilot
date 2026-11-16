import java.nio.file.Files;
import java.nio.file.Path;

/** Small dependency-free regression tests; run with java -ea PrepPilotTest. */
public class PrepPilotTest {
    public static void main(String[] args) throws Exception {
        Path directory = Files.createTempDirectory("preppilot-test");
        Path file = directory.resolve("problems.txt");
        PrepPilot app = new PrepPilot(file);
        app.addProblem(" Two Sum ", " Arrays ", "easy");
        require(Files.readString(file).contains("1|Two Sum|Arrays|Easy|Unsolved"), "add and normalize");
        app.updateStatus(1, "solved");
        require(Files.readString(file).contains("|Solved"), "update and persist status");
        PrepPilot reloaded = new PrepPilot(file);
        reloaded.addProblem("Binary Search", "Searching", "Medium");
        require(Files.readString(file).contains("2|Binary Search"), "reload next ID");
        reloaded.addProblem("   ", "Arrays", "Easy");
        require(!Files.readString(file).contains("3|"), "reject blank title");
        expectInvalid(() -> new Problem(0, "x", "y", "Easy", "Unsolved"));
        expectInvalid(() -> new Problem(1, "x", "y", "Extreme", "Unsolved"));
        expectInvalid(() -> new Problem(1, "x", "y", "Easy", "Unknown"));
        System.out.println("All PrepPilot tests passed.");
    }

    private static void require(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    private static void expectInvalid(Runnable action) {
        try { action.run(); } catch (IllegalArgumentException expected) { return; }
        throw new AssertionError("Expected invalid input to be rejected.");
    }
}
