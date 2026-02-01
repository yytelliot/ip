package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.TaskList;

/**
 * Abstract class representing a command.
 */
public abstract class Command {
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
}