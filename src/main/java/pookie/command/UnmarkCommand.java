package pookie.command;

import java.util.List;

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

        String[] indicesArgs = new String[args.length - 1];
        System.arraycopy(args, 1, indicesArgs, 0, args.length - 1);

        List<Integer> indices = parseTaskIndices(taskList, indicesArgs);
        List<Task> tasks = getTasksByIndices(taskList, indices);

        for (Task task : tasks) {
            task.markAsUndone();
        }
        saveTaskList(taskList, storage);

        String header = indices.size() == 1
                ? "OK, I've marked this task as not done yet ;w;"
                : "OK, I've marked these tasks as not done yet ;w;";
        return formatTasksWithIndices(header, indices, tasks);
    }
}
