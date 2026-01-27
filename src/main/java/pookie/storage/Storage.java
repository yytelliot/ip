package pookie.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import pookie.task.DeadlineTask;
import pookie.task.EventTask;
import pookie.task.Task;
import pookie.task.TaskList;
import pookie.task.TodoTask;

/**
 * Handles storage of Pookie data like the Task List.
 */
public class Storage {

    private final Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    public void ensureFileExists() throws IOException {
        if (filePath.getParent() != null) {
            Files.createDirectories(filePath.getParent());
        }
        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }
    }

    /**
     * Loads tasks from the storage file into the TaskList.
     * 
     * @throws IOException
     */
    public void loadIntoTaskList() throws IOException {
        ensureFileExists();
        TaskList.clearTasks();
        Scanner sc = new Scanner(filePath);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isBlank()) {
                continue;
            }

            String[] parts = line.split("\\\\s*\\\\|\\\\s*");

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            Task task;

            switch (type) {
                case "T" -> task = new TodoTask(description);
                case "D" -> {
                    String by = parts[3];
                    task = new DeadlineTask(description, by);
                }
                case "E" -> {
                    String from = parts[3];
                    String to = parts[4];
                    task = new EventTask(description, from, to);
                }
                default -> {
                    continue; // Skip unrecognized task types
                }
                }

            if (isDone) {
                task.markAsDone();
            }
        }

    }

    /**
     * Saves the current TaskList to the storage file.
     * 
     * @throws IOException
     */
    public void saveTaskList() throws IOException {
        ensureFileExists();

        StringBuilder sb = new StringBuilder();
        int taskCount = TaskList.getTaskCount();

        for (int i = 0; i < taskCount; i++) {
            Task task = TaskList.getTask(i);
            String type = task.getType();
            String statusIcon = task.getStatus() ? "1" : "0";
            String description = task.getDescription();

            sb.append(type).append(" | ").append(statusIcon).append(" | ").append(description);

            if (task instanceof DeadlineTask deadlineTask) {
                String byTime = deadlineTask.getByTime();
                sb.append(" | ").append(byTime);
            } else if (task instanceof EventTask eventTask) {
                String fromTime = eventTask.getFromTime();
                String toTime = eventTask.getToTime();
                sb.append(" | ").append(fromTime).append(" | ").append(toTime);
            }

            sb.append("\n");
        }

        Files.writeString(filePath, sb.toString());
    }

}
