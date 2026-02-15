package pookie.command;

import java.io.IOException;

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
}
