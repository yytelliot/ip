package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;
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
        return executeBatchOperation(args, taskList, storage,
                task -> task.markAsUndone(),
                "OK, I've marked this task as not done yet ;w;",
                "OK, I've marked these tasks as not done yet ;w;");
    }
}
