package pookie.command;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.Task;
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
        if (args.length < MIN_INDEX_COMMAND_ARGS) {
            throw new PookieException("Please provide the index of the task to mark! >w<");
        }

        Task task = getTaskByIndex(taskList, args[1]);
        task.markAsDone();
        saveTaskList(taskList, storage);

        return "Nice! Pookie will mark this task as done x3\n  " + task.toString();
    }
}
