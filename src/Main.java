import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        PrepPilot tracker = new PrepPilot();

        boolean running = true;

        while (running) {

            System.out.println("\n===== PrepPilot =====");
            System.out.println("1. Add a DSA Problem");
            System.out.println("2. View All Problems");
            System.out.println("3. Update Problem Status");
            System.out.println("4. Search Problems");
            System.out.println("5. Filter by Difficulty");
            System.out.println("6. View Statistics");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {

                case "1":
                    System.out.print("Enter problem title: ");
                    String title = scanner.nextLine().trim();

                    System.out.print("Enter topic: ");
                    String topic = scanner.nextLine().trim();

                    if (title.isEmpty() || topic.isEmpty()) {
                        System.out.println("Title and topic cannot be blank.");
                        break;
                    }

                    System.out.print(
                            "Enter difficulty (Easy/Medium/Hard): "
                    );
                    String difficulty = scanner.nextLine().trim();

                    try {
                        tracker.addProblem(
                                title, topic, difficulty
                        );
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    tracker.viewProblems();
                    break;

                case "3":
                    System.out.print("Enter problem ID: ");
                    try {
                        int id = Integer.parseInt(
                                scanner.nextLine().trim()
                        );

                        System.out.println("1. Solved");
                        System.out.println("2. Unsolved");
                        System.out.println("3. Needs Revision");

                        System.out.print("Choose new status: ");
                        String statusChoice = scanner.nextLine().trim();

                        String status = null;

                        switch (statusChoice) {
                            case "1":
                                status = "Solved";
                                break;
                            case "2":
                                status = "Unsolved";
                                break;
                            case "3":
                                status = "Needs Revision";
                                break;
                            default:
                                System.out.println(
                                        "Invalid status choice."
                                );
                        }

                        if (status != null) {
                            tracker.updateStatus(id, status);
                        }

                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Please enter a valid numeric ID."
                        );
                    }
                    break;

                case "4":
                    System.out.print(
                            "Enter title or topic to search: "
                    );
                    String keyword = scanner.nextLine();

                    tracker.searchProblems(keyword);
                    break;

                case "5":
                    System.out.print(
                            "Enter difficulty (Easy/Medium/Hard): "
                    );
                    String filter = scanner.nextLine().trim();

                    try {
                        tracker.filterByDifficulty(filter);
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "6":
                    tracker.showStatistics();
                    break;

                case "7":
                    running = false;
                    System.out.println(
                            "Thank you for using PrepPilot!"
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice. Please try again."
                    );
            }
        }

        scanner.close();
    }
}
