package pookie.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.Task;
import pookie.task.TaskList;
import pookie.util.IndexParser;
import pookie.util.TaskFormatter;

/**
 * Abstract class representing a command.
 */
public abstract class Command {

    /** Minimum number of arguments required for index-based commands. */
    protected static final int MIN_INDEX_COMMAND_ARGS = 2;

    private static final IndexParser INDEX_PARSER = new IndexParser();
    private static final TaskFormatter FORMATTER = new TaskFormatter();

    /**
     * Executes the command with the given task list and storage.
     *
     * @param taskList the task list to operate on
     * @param storage the storage to save changes to
     * @return the message to print after execution
     */
    public abstract String execute(TaskList taskList, Storage storage) throws PookieException;

    /**
     * Indicates whether this command should close the application.
     *
     * @return true if the command is an exit command, false otherwise
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Retrieves a task by its 1-indexed position from the argument.
     * Converts user input (1-indexed) to internal 0-indexed format.
     *
     * @param taskList the task list to retrieve from
     * @param indexStr the string representation of the task index (1-indexed)
     * @return the Task at the specified index
     * @throws PookieException if index is not a valid number or is out of bounds
     */
    protected Task getTaskByIndex(TaskList taskList, String indexStr) throws PookieException {
        assert taskList != null : "Task list must not be null";
        assert indexStr != null : "Index string must not be null";
        try {
            int index = Integer.parseInt(indexStr) - 1;
            return taskList.getTask(index);
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index provided is not a number! >w<!");
        } catch (IndexOutOfBoundsException e) {
            throw new PookieException("Owo? That task index doesn't exist! >w<!");
        }
    }

    /**
     * Saves the task list to storage and wraps IOException in PookieException.
     *
     * @param taskList the task list to save
     * @param storage the storage to save to
     * @throws PookieException if saving fails
     */
    protected void saveTaskList(TaskList taskList, Storage storage) throws PookieException {
        try {
            storage.saveTaskList(taskList);
        } catch (IOException e) {
            throw new PookieException("I couldn't save your tasks. Please try again. >w<");
        }
    }

    /**
     * Formats a list of tasks with numbering.
     *
     * @param taskList the task list to format
     * @param header the header message
     * @return a formatted string with numbered tasks
     */
    protected String formatTaskList(TaskList taskList, String header) {
        return FORMATTER.formatTaskList(taskList, header);
    }

    /**
     * Retrieves tasks by their 0-based indices.
     *
     * @param taskList the task list to retrieve from
     * @param indices list of 0-based indices
     * @return list of tasks at the specified indices
     */
    protected List<Task> getTasksByIndices(TaskList taskList, List<Integer> indices) {
        List<Task> tasks = new ArrayList<>();
        for (int index : indices) {
            tasks.add(taskList.getTask(index));
        }
        return tasks;
    }

    /**
     * Formats a list of tasks, displaying each with its original position number.
     *
     * @param header the header message to display
     * @param indices the 0-based indices corresponding to each task's original position
     * @param tasks the tasks to display
     * @return a string with the header followed by tasks labeled with their original 1-based positions
     */
    protected String formatTasksWithIndices(String header, List<Integer> indices, List<Task> tasks) {
        return FORMATTER.formatTasksWithIndices(header, indices, tasks);
    }

    /**
     * Parses task indices from user input, supporting both single indices and ranges.
     *
     * @param taskList the task list for validation
     * @param indexStrs array of index strings to parse
     * @return list of 0-based indices
     * @throws PookieException if any index is invalid or out of bounds
     */
    protected List<Integer> parseTaskIndices(TaskList taskList, String[] indexStrs) throws PookieException {
        return INDEX_PARSER.parseTaskIndices(taskList, indexStrs);
    }

    /**
     * Functional interface for applying an operation to a task.
     */
    @FunctionalInterface
    protected interface TaskOperation {
        void apply(Task task);
    }

    /**
     * Executes a batch operation on multiple tasks (mark, unmark, delete).
     * Handles common logic: parse indices, get tasks, apply operation, save, format response.
     *
     * @param args the command arguments
     * @param taskList the task list to operate on
     * @param storage the storage to persist changes
     * @param operation the operation to apply to each task
     * @param singularMsg message for single task
     * @param pluralMsg message for multiple tasks
     * @return the formatted response message
     * @throws PookieException if validation or parsing fails
     */
    protected String executeBatchOperation(String[] args, TaskList taskList, Storage storage,
            TaskOperation operation, String singularMsg, String pluralMsg) throws PookieException {
        if (args.length < MIN_INDEX_COMMAND_ARGS) {
            throw new PookieException("Please provide at least one task index!");
        }

        String[] indicesArgs = new String[args.length - 1];
        System.arraycopy(args, 1, indicesArgs, 0, args.length - 1);

        List<Integer> indices = parseTaskIndices(taskList, indicesArgs);
        List<Task> tasks = getTasksByIndices(taskList, indices);

        for (Task task : tasks) {
            operation.apply(task);
        }
        saveTaskList(taskList, storage);

        String header = indices.size() == 1 ? singularMsg : pluralMsg;
        return formatTasksWithIndices(header, indices, tasks);
    }
}
