# PrepPilot

PrepPilot is a Java-based console application that helps users organize and track their Data Structures and Algorithms (DSA) practice. It allows users to manage coding problems, monitor solving progress, and maintain their practice records using local file storage.

## Features

- Add DSA problems with a title, topic, and difficulty.
- View all stored problems.
- Update problem status:
  - Solved
  - Unsolved
  - Needs Revision
- Search problems by title or topic.
- Filter problems by difficulty: Easy, Medium, or Hard.
- View preparation statistics, including total problems and completion percentage.
- Persist problem records using a local text file.
- Load previously saved problems when the application restarts.
- Validate user input and handle invalid records.
- Handle duplicate IDs and integer ID boundary conditions.
- Use temporary-file replacement when saving data to reduce the risk of corrupting existing records.

## Technology Stack

- **Language:** Java
- **User Interface:** Console-based menu
- **Storage:** Local text file
- **Testing:** Dependency-free Java test programs
- **JDK:** Java 11 or newer

## Project Structure

PrepPilot/
|
|-- src/
|   |-- Main.java
|   |-- PrepPilot.java
|   |-- Problem.java
|
|-- tests/
|   |-- PrepPilotTest.java
|   |-- EmptyStatisticsTest.java
|   |-- DataLoadingTest.java
|   |-- DataSaveFailureTest.java
|   |-- NullPathTest.java
|   |-- IdOverflowTest.java
|   |-- IdOverflowBoundaryTest.java
|
|-- data/
|   |-- problems.txt
|
|-- out/                  Generated compiled classes (excluded from Git)
|-- .gitignore
|-- README.md

## Architecture

### 1. Problem.java

Represents an individual DSA problem.

Responsibilities:
- Store problem ID, title, topic, difficulty, and status.
- Validate required fields.
- Normalize difficulty and status values.
- Provide getters and a status setter.
- Display problem information.
- Convert a problem into its file-storage format.

### 2. PrepPilot.java

Contains the application's main business logic.

Responsibilities:
- Add and manage problems.
- View all problems.
- Update problem status.
- Search by title or topic.
- Filter by difficulty.
- Calculate preparation statistics.
- Save and load problem records.
- Validate loaded records and handle duplicate IDs.
- Handle ID overflow and saving failures.

### 3. Main.java

Provides the console-based user interface.

Responsibilities:
- Display the application menu.
- Accept user input.
- Call the appropriate PrepPilot operations.
- Continue running until the user chooses to exit.

## Requirements

- Java Development Kit (JDK) 11 or newer.
- A terminal or command prompt.
- A code editor such as Visual Studio Code (optional).

Verify Java installation:

```bash
java -version
javac -version
```

## How to Run

Open a terminal in the project root directory.

### Compile

```bash
javac -d out src/*.java
```

### Run

```bash
java -cp out Main
```

The application stores its records in:

```text
data/problems.txt
```

The data file is read and written relative to the project working directory.

## How to Run Tests

The project uses seven dependency-free test programs.

### Compile application and all tests

```bash
javac -d out src/*.java tests/*.java
```

### Execute the tests

```bash
java -ea -cp out PrepPilotTest
java -cp out EmptyStatisticsTest
java -cp out DataLoadingTest
java -cp out DataSaveFailureTest
java -cp out NullPathTest
java -cp out IdOverflowTest
java -cp out IdOverflowBoundaryTest
```

### Test coverage

| Test | Purpose |
|---|---|
| PrepPilotTest | Adding problems, input normalization, status updates, persistence, and validation |
| EmptyStatisticsTest | Statistics when no problems exist |
| DataLoadingTest | Valid records, duplicate IDs, and malformed records |
| DataSaveFailureTest | Handling save failures and rolling back unsuccessful additions |
| NullPathTest | Rejecting a null data-file path |
| IdOverflowTest | Preventing additions after the maximum ID is reached |
| IdOverflowBoundaryTest | Correct handling of the final available integer ID |

## Data Storage

Problem records are stored in a pipe-separated text format:

```text
ID|Title|Topic|Difficulty|Status
```

Example:

```text
1|Two Sum|Arrays|Easy|Needs Revision
2|Reverse Linked List|Linked List|Medium|Unsolved
```

The application creates the data directory when needed and saves records using UTF-8 encoding.

## Future Improvements

Potential extensions for future versions include:

- Graphical user interface.
- Database integration.
- Due dates and study reminders.
- Sorting and advanced filtering.
- Exporting preparation statistics.
- More detailed progress tracking.

## GitHub Setup

1. Create a new empty repository on GitHub.
2. Open a terminal in the PrepPilot project folder.
3. Initialize Git:

```bash
git init
```

4. Review the files you intend to publish:

```bash
git status
```

5. Stage the project:

```bash
git add README.md .gitignore src tests
```

6. Commit:

```bash
git commit -m "Add PrepPilot Java DSA tracker"
```

7. Set the main branch:

```bash
git branch -M main
```

8. Connect your GitHub repository:

```bash
git remote add origin https://github.com/YOUR-USERNAME/YOUR-REPOSITORY.git
```

9. Push the project:

```bash
git push -u origin main
```

Replace the remote URL with your actual GitHub repository URL.

**Note:** Your existing `data/problems.txt` contains your personal practice progress. Review it before publishing. The commands above intentionally do not stage it.

## Project Status

PrepPilot's implemented features have been manually tested, and all seven automated test programs have passed in the development environment.