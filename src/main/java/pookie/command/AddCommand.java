package pookie.command;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import pookie.exception.PookieException;
import pookie.format.Formats;
import pookie.storage.Storage;
import pookie.task.DeadlineTask;
import pookie.task.EventTask;
import pookie.task.Task;
import pookie.task.TaskList;
import pookie.task.TodoTask;

/**
 * Command to add a new task to the task list. Supported task types:
 * - todo {@code <description>}
 * - deadline {@code <description>}/by {@code <time>}
 * - event {@code <description>} /from {@code <start time>} /to {@code <end time>}
 */
public class AddCommand extends Command {

    private final String[] args;

    /**
     * Constructs an AddCommand with the given arguments.
     *
     * @param args the command arguments
     * @param taskList the task list to add the task to
     */
    public AddCommand(String[] args) {
        this.args = args;
    }

    /**
     * Find the index of a token in an array starting from a given index.
     *
     * @param arr the array to search
     * @param start the starting index
     * @param token the token to find
     * @return the index of the token, or -1 if not found
     */
    private static int findToken(String[] arr, int start, String token) {
        for (int i = start; i < arr.length; i++) {
            if (arr[i].equals(token)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Joins a subarray of strings into a single string with spaces.
     *
     * @param arr the array of strings
     * @param start the starting index (inclusive)
     * @param endExclusive the ending index (exclusive)
     * @return the joined string
     */
    private static String join(String[] arr, int start, int endExclusive) {
        if (start >= endExclusive) {
            return "";
        }
        return String.join(" ", Arrays.copyOfRange(arr, start, endExclusive)).trim();
    }

    @Override
    public String execute(TaskList taskList, Storage storage) throws PookieException {
        if (args.length < 2) {
            throw new PookieException("Please provide a task to add! >w<");
        }

        Task task = createTask(args[0]);
        saveAndRespond(taskList, storage, task);
        return buildResponseMessage(task, taskList);
    }

    /**
     * Creates a task based on the specified task type.
     *
     * @param taskType the type of task to create ('todo', 'deadline', or 'event')
     * @return the created Task object
     * @throws PookieException if the task type is unknown
     */
    private Task createTask(String taskType) throws PookieException {
        return switch (taskType) {
            case "todo" -> createTodoTask();
            case "deadline" -> createDeadlineTask();
            case "event" -> createEventTask();
            default -> throw new PookieException("Pookie doesn't know the task: " + taskType
                    + ". Please use 'todo', 'deadline', or 'event'. ;w;");
        };
    }

    /**
     * Creates a todo task from the command arguments.
     *
     * @return a new TodoTask with the provided description
     * @throws PookieException if the description is empty
     */
    private Task createTodoTask() throws PookieException {
        String description = join(args, 1, args.length);
        if (description.isEmpty()) {
            throw new PookieException("The description of a todo cannot be empty! >w<");
        }
        return new TodoTask(description);
    }

    /**
     * Creates a deadline task from the command arguments.
     * Expects format: deadline <description> /by <date>
     *
     * @return a new DeadlineTask with the provided description and deadline
     * @throws PookieException if required fields are missing or date format is invalid
     */
    private Task createDeadlineTask() throws PookieException {
        int byIdx = findToken(args, 1, "/by");
        if (byIdx == -1) {
            throw new PookieException("Please provide a description and deadline using '/by'! >w<");
        }

        String taskDescription = join(args, 1, byIdx);
        String byTime = join(args, byIdx + 1, args.length);

        if (taskDescription.isEmpty() || byTime.isEmpty()) {
            throw new PookieException("The description or deadline of the deadline cannot be empty! >w<");
        }

        LocalDate byDate = parseInputDate(byTime);
        if (byDate == null) {
            throw new PookieException(">w<! I don't know this format: \"" + byTime + "\". "
                    + "Please use something like: Jan 28 2026 or 2026-01-28 ^w^");
        }
        return new DeadlineTask(taskDescription, byDate);
    }

    /**
     * Creates an event task from the command arguments.
     * Expects format: event <description> /from <start date> /to <end date>
     *
     * @return a new EventTask with the provided description and date range
     * @throws PookieException if required fields are missing or date formats are invalid
     */
    private Task createEventTask() throws PookieException {
        int fromIdx = findToken(args, 1, "/from");
        int toIdx = findToken(args, 1, "/to");

        if (fromIdx == -1 || toIdx == -1 || fromIdx >= toIdx) {
            throw new PookieException("Please provide event description and time using '/from' and '/to'! >w<");
        }

        String eventDescription = join(args, 1, fromIdx);
        String fromTime = join(args, fromIdx + 1, toIdx);
        String toTime = join(args, toIdx + 1, args.length);

        if (eventDescription.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
            throw new PookieException(
                    "The description, from time, and to time of the event cannot be empty! >w<");
        }

        LocalDate fromDate = parseInputDate(fromTime);
        LocalDate toDate = parseInputDate(toTime);

        if (fromDate == null) {
            throw new PookieException(">w<! Pookie doesn't know this format: \"" + fromTime + "\". "
                    + "Please use something like: Jan 28 2026 or 2026-01-28 ^w^");
        }

        if (toDate == null) {
            throw new PookieException(">w<! Pookie doesn't don't know this format: \"" + toTime + "\". "
                    + "Please use something like: Jan 28 2026 or 2026-01-28 ^w^");
        }

        return new EventTask(eventDescription, fromDate, toDate);
    }

    /**
     * Saves the task to the task list and storage.
     *
     * @param taskList the task list to add the task to
     * @param storage the storage to persist changes
     * @param task the task to save
     * @throws PookieException if saving fails
     */
    private void saveAndRespond(TaskList taskList, Storage storage, Task task) throws PookieException {
        try {
            taskList.addTask(task);
            storage.saveTaskList(taskList);
        } catch (IllegalStateException | IOException e) {
            throw new PookieException(e.getMessage());
        }
    }

    /**
     * Builds the response message for successfully adding a task.
     *
     * @param task the task that was added
     * @param taskList the updated task list
     * @return a formatted response message
     */
    private String buildResponseMessage(Task task, TaskList taskList) {
        return """
            Pookie has added your task! ^w^
              %s
            Now you have %d tasks in the list! UwU
            """.formatted(task, taskList.getTaskCount());
    }

    private LocalDate parseInputDate(String dateStr) {
        for (DateTimeFormatter f : Formats.ACCEPTED_INPUT_FORMATS) {
            try {
                return LocalDate.parse(dateStr, f);
            } catch (Exception e) {
                // Try next format
            }
        }

        return null;
    }
}
