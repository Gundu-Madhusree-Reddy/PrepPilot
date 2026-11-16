import java.nio.file.Files;
import java.nio.file.Path;

public class DataLoadingTest {

    public static void main(String[] args) throws Exception {

        Path directory =
                Files.createTempDirectory("preppilot-load-test");

        Path file = directory.resolve("problems.txt");

        String data =
                "1|Two Sum|Arrays|Easy|Solved\n" +
                "1|Duplicate Problem|Arrays|Medium|Unsolved\n" +
                "2|Reverse Linked List|Linked List|Medium|Unsolved\n" +
                "3|Invalid Record|Arrays|Easy\n";

        Files.writeString(file, data);

        PrepPilot tracker = new PrepPilot(file);

        tracker.viewProblems();
    }
}