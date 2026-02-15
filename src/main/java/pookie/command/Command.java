package pookie.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.Task;
import pookie.task.TaskList;

/**
 * Abstract class representing a command.
 */
public abstract class Command {

    /** Minimum number of arguments required for index-based commands. */
    protected static final int MIN_INDEX_COMMAND_ARGS = 2;

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
     * Used by commands that display multiple tasks (List, Find).
     *
     * @param taskList the task list to format
     * @param header the header message
     * @return a formatted string with numbered tasks
     */
    protected String formatTaskList(TaskList taskList, String header) {
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (int i = 0; i < taskList.getTaskCount(); i++) {
            sb.append((i + 1)).append(". ")
                    .append(taskList.getTask(i))
                    .append("\n");
        }
        return sb.toString().trim();
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
     * Formats a list of tasks, displaying each with its original position number from the task list.
     * Used to show which specific tasks were affected by batch operations.
     *
     * @param header the header message to display
     * @param indices the 0-based indices corresponding to each task's original position
     * @param tasks the tasks to display
     * @return a string with the header followed by tasks labeled with their original 1-based positions
     */
    protected String formatTasksWithIndices(String header, List<Integer> indices, List<Task> tasks) {
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("  ").append(indices.get(i) + 1).append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    protected List<Integer> parseTaskIndices(TaskList taskList, String[] indexStrs) throws PookieException {
        List<Integer> indices = new ArrayList<>();
        for (String indexStr : indexStrs) {
            if (indexStr.contains("-")) {
                indices.addAll(parseRange(taskList, indexStr));
                continue;
            }

            indices.add(parseSingleIndex(taskList, indexStr));
        }
        return indices;
    }

    private List<Integer> parseRange(TaskList taskList, String rangeStr) throws PookieException {
        String[] parts = rangeStr.split("-", -1);
        List<Integer> indices = new ArrayList<>();
        if (parts.length != 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
            throw new PookieException("Owo? The index range " + rangeStr + " is invalid! >w<!");
        }

        int startIndex = parseSingleIndex(taskList, parts[0]);
        int endIndex = parseSingleIndex(taskList, parts[1]);
        if (startIndex > endIndex) {
            throw new PookieException("Owo? The index range " + rangeStr + " is invalid! >w<!");
        }
        for (int i = startIndex; i <= endIndex; i++) {
            indices.add(i);
        }
        return indices;
    }

    private int parseSingleIndex(TaskList taskList, String indexStr) throws PookieException {
        try {
            int index = Integer.parseInt(indexStr) - 1;
            if (index < 0 || index >= taskList.getTaskCount()) {
                throw new IndexOutOfBoundsException();
            }
            return index;
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index " + indexStr + " is not a number! >w<!");
        } catch (IndexOutOfBoundsException e) {
            throw new PookieException("Owo? The task index " + indexStr + " doesn't exist! >w<!");
        }
    }
}
