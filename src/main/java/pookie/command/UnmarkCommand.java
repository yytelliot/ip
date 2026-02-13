package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.Task;
import pookie.task.TaskList;

/**
 * Command to unmark a task as not done.
 */
public class UnmarkCommand extends Command {

    private final String[] args;

    public UnmarkCommand(String[] args) {
        this.args = args;
    }

    @Override
    public String execute(TaskList taskList, Storage storage) throws PookieException {
        if (args.length < MIN_INDEX_COMMAND_ARGS) {
            throw new PookieException("Please provide the index of the task to unmark! >w<");
        }

        Task task = getTaskByIndex(taskList, args[1]);
        task.markAsUndone();
        saveTaskList(taskList, storage);

        return "OK, I've marked this task as not done yet ;w;\n  " + task.toString();
    }
}
