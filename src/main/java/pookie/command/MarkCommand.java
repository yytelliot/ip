package pookie.command;

import java.util.List;

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

        String[] indicesArgs = new String[args.length - 1];
        System.arraycopy(args, 1, indicesArgs, 0, args.length - 1);

        List<Integer> indices = parseTaskIndices(taskList, indicesArgs);
        List<Task> tasks = getTasksByIndices(taskList, indices);

        for (Task task : tasks) {
            task.markAsDone();
        }
        saveTaskList(taskList, storage);

        String header = indices.size() == 1
                ? "Nice! Pookie will mark this task as done x3"
                : "Nice! Pookie will mark these tasks as done x3";
        return formatTasksWithIndices(header, indices, tasks);
    }
}
