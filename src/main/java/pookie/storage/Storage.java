package pookie.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
        assert filePath != null : "Storage file path must not be null";
        this.filePath = filePath;
    }

    /**
     * Ensures that the storage file exists, creating it if necessary.
     *
     * @throws IOException
     */
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
    public void loadIntoTaskList(TaskList taskList) throws IOException {
        ensureFileExists();
        taskList.clearTasks();
        try (Scanner sc = new Scanner(filePath)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = parseLine(line);
                Task task = parseTask(parts);
                if (task != null) {
                    if (parts[1].equals("1")) {
                        task.markAsDone();
                    }
                    taskList.addTask(task);
                }
            }
        }
    }

    /**
     * Parses a line from the storage file into task components.
     * Splits by '|' delimiter and trims whitespace.
     *
     * @param line the line to parse
     * @return an array of trimmed task components
     */
    private String[] parseLine(String line) {
        assert line != null : "Storage line must not be null";
        String[] parts = line.split("\\|");
        for (int i = 0; i < parts.length; i++) {
            parts[i] = parts[i].trim();
        }
        return parts;
    }

    /**
     * Parses task components into a Task object based on task type.
     * Delegates to specific parsers for each task type.
     *
     * @param parts the parsed task components [type, status, description, ...]
     * @return the created Task object, or null if type is unknown
     */
    private Task parseTask(String[] parts) {
        String type = parts[0];
        String description = parts[2];

        return switch (type) {
            case "T" -> new TodoTask(description);
            case "D" -> parseDeadlineTask(description, parts);
            case "E" -> parseEventTask(description, parts);
            default -> null;
        };
    }

    /**
     * Parses a deadline task from storage components.
     * Extracts the deadline date from parts[3].
     *
     * @param description the task description
     * @param parts the task components array [type, status, description, deadline, ...]
     * @return a new DeadlineTask with the parsed data
     */
    private Task parseDeadlineTask(String description, String[] parts) {
        String by = parts[3];
        LocalDate byDate = parseDateWithFormats(by, Formats.ACCEPTED_INPUT_FORMATS);
        if (byDate == null) {
            return null;
        }
        return new DeadlineTask(description, byDate);
    }

    /**
     * Parses an event task from storage components.
     * Extracts start date from parts[3] and end date from parts[4].
     *
     * @param description the task description
     * @param parts the task components array [type, status, description, fromDate, toDate]
     * @return a new EventTask with the parsed data
     */
    private Task parseEventTask(String description, String[] parts) {
        LocalDate fromDate = parseDateWithFormat(parts[3], Formats.STORAGE_DATE);
        LocalDate toDate = parseDateWithFormat(parts[4], Formats.STORAGE_DATE);
        if (fromDate == null || toDate == null) {
            return null;
        }
        return new EventTask(description, fromDate, toDate);
    }

    /**
     * Parses a date string using multiple allowed date formats.
     * Tries each format in sequence until one succeeds.
     *
     * @param dateStr the date string to parse
     * @param formatters list of DateTimeFormatters to try
     * @return the parsed LocalDate, or null if no format matches
     */
    private LocalDate parseDateWithFormats(String dateStr, List<DateTimeFormatter> formatters) {
        for (DateTimeFormatter f : formatters) {
            try {
                return LocalDate.parse(dateStr, f);
            } catch (Exception e) {
                // Try next format
            }
        }
        return null;
    }

    /**
     * Parses a date string using a single date format.
     *
     * @param dateStr the date string to parse
     * @param formatter the DateTimeFormatter to use
     * @return the parsed LocalDate, or null if parsing fails
     */
    private LocalDate parseDateWithFormat(String dateStr, DateTimeFormatter formatter) {
        try {
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Saves the current TaskList to the storage file.
     *
     * @throws IOException
     */
    public void saveTaskList(TaskList taskList) throws IOException {
        ensureFileExists();

        StringBuilder sb = new StringBuilder();
        int taskCount = taskList.getTaskCount();

        for (int i = 0; i < taskCount; i++) {
            Task task = taskList.getTask(i);
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
