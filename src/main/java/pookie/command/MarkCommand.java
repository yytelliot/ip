package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.TaskList;

/**
 * Command to mark a task as done.
 */
public class MarkCommand extends Command {

    private final String[] args;

    public MarkCommand(String[] args) {
        this.args = args;
    }

    @Override
    public String execute(TaskList taskList, Storage storage) throws PookieException {
        return executeBatchOperation(args, taskList, storage,
                task -> task.markAsDone(),
                "Nice! Pookie will mark this task as done x3",
                "Nice! Pookie will mark these tasks as done x3");
    }
}
