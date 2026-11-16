public class Problem {

    private final int id;
    private final String title;
    private final String topic;
    private final String difficulty;
    private String status;

    public Problem(int id, String title, String topic,
                   String difficulty, String status) {

        if (id <= 0) throw new IllegalArgumentException("ID must be positive.");
        this.id = id;
        this.title = requireText(title, "Title");
        this.topic = requireText(topic, "Topic");
        this.difficulty = normalizeDifficulty(difficulty);
        this.status = normalizeStatus(status);
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTopic() {
        return topic;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = normalizeStatus(status);
    }

    private static String requireText(String value, String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(field + " cannot be blank.");
        }
        return value.trim().replace("|", "/").replace("\n", " ").replace("\r", " ");
    }

    public static String normalizeDifficulty(String value) {
        if (value == null) throw new IllegalArgumentException("Choose Easy, Medium, or Hard.");
        for (String option : new String[] {"Easy", "Medium", "Hard"}) {
            if (option.equalsIgnoreCase(value.trim())) return option;
        }
        throw new IllegalArgumentException("Choose Easy, Medium, or Hard.");
    }

    public static String normalizeStatus(String value) {
        if (value == null) throw new IllegalArgumentException("Choose Solved, Unsolved, or Needs Revision.");
        for (String option : new String[] {"Solved", "Unsolved", "Needs Revision"}) {
            if (option.equalsIgnoreCase(value.trim())) return option;
        }
        throw new IllegalArgumentException("Choose Solved, Unsolved, or Needs Revision.");
    }

    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Title: " + title);
        System.out.println("Topic: " + topic);
        System.out.println("Difficulty: " + difficulty);
        System.out.println("Status: " + status);
    }

    public String toFileFormat() {
        return id + "|" + title + "|" + topic + "|"
                + difficulty + "|" + status;
    }
}
