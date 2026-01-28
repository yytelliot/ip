package pookie.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import pookie.format.Formats;
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

            String[] parts = line.split("\\|");
            for (int i = 0; i < parts.length; i++) {
                parts[i] = parts[i].trim();
            }

            String type = parts[0];
            boolean isDone = parts[1].equals("1");
            String description = parts[2];
            Task task;

            switch (type) {
                case "T" -> task = new TodoTask(description);
                case "D" -> {
                    String by = parts[3];
                    LocalDate byDate = null;

                    for (DateTimeFormatter f : Formats.ACCEPTED_INPUT_FORMATS) {
                        try {
                            byDate = LocalDate.parse(by, f);
                            break;
                        } catch (Exception e) {
                            // Try next format
                        }
                    }

                    if (byDate == null) {
                        // If no format matched, skip this task
                    }

                    task = new DeadlineTask(description, byDate);

                }
                case "E" -> {
                    String from = parts[3];
                    String to = parts[4];

                    LocalDate fromDate = null;
                    LocalDate toDate = null;

                    try {
                        fromDate = LocalDate.parse(from, Formats.STORAGE_DATE);
                    } catch (Exception e) {
                        // do nothingk
                    }

                    try {
                        toDate = LocalDate.parse(to, Formats.STORAGE_DATE);
                    } catch (Exception e) {
                        // do nothing
                    }

                    if (fromDate == null || toDate == null) {
                        // skip this task
                    }


                    task = new EventTask(description, fromDate, toDate);
                }
                default -> {
                    continue; // Skip unrecognized task types
                }
                }

            if (isDone) {
                task.markAsDone();
            }

            TaskList.addTask(task);
        }

        sc.close();

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
                String byTime = deadlineTask.getByTime().format(Formats.STORAGE_DATE);
                sb.append(" | ").append(byTime);
            } else if (task instanceof EventTask eventTask) {
                String fromTime = eventTask.getFromTime().format(Formats.STORAGE_DATE);
                String toTime = eventTask.getToTime().format(Formats.STORAGE_DATE);
                sb.append(" | ").append(fromTime).append(" | ").append(toTime);
            }

            sb.append("\n");
        }

        Files.writeString(filePath, sb.toString());
    }

}
