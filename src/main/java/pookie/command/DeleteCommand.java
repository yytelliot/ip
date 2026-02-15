package pookie.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import pookie.exception.PookieException;
import pookie.storage.Storage;
import pookie.task.Task;
import pookie.task.TaskList;

/**
 * Command to delete a task.
 */
public class DeleteCommand extends Command {

    private final String[] args;

    public DeleteCommand(String[] args) {
        this.args = args;
    }

    @Override
    public String execute(TaskList taskList, Storage storage) throws PookieException {
        if (args.length < MIN_INDEX_COMMAND_ARGS) {
            throw new PookieException(">w<! Please provide the index of the task to delete!");
        }

        String[] indicesArgs = new String[args.length - 1];
        System.arraycopy(args, 1, indicesArgs, 0, args.length - 1);

        List<Integer> indices = parseTaskIndices(taskList, indicesArgs);
        List<Task> tasksToDelete = getTasksByIndices(taskList, indices);

        // Delete in descending order to avoid index shifting
        List<Integer> sortedIndices = new ArrayList<>(indices);
        sortedIndices.sort(Collections.reverseOrder());
        for (int index : sortedIndices) {
            taskList.deleteTask(index);
        }
        saveTaskList(taskList, storage);

        String header = indices.size() == 1
                ? "I've deleted this task! >:3"
                : "I've deleted these tasks! >:3";
        return formatTasksWithIndices(header, indices, tasksToDelete);
    }

}
