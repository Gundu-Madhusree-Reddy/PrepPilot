public class NullPathTest {

    public static void main(String[] args) {

        try {
            PrepPilot tracker = new PrepPilot(null);

            throw new AssertionError(
                    "Expected NullPointerException."
            );

        } catch (NullPointerException e) {

            System.out.println(
                    "Null path correctly rejected: "
                    + e.getMessage()
            );

            System.out.println("NullPathTest passed!");
        }
    }
}