import java.nio.file.Files;
import java.nio.file.Path;

public class EmptyStatisticsTest {

    public static void main(String[] args) throws Exception {

        Path directory =
                Files.createTempDirectory("preppilot-empty-test");

        Path emptyFile = directory.resolve("problems.txt");

        PrepPilot tracker = new PrepPilot(emptyFile);

        tracker.showStatistics();
    }
}