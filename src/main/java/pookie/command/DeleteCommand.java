package pookie.command;

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

        Task removed = deleteTaskByIndex(taskList, storage);
        return "I've deleted this task! >:3\n  " + removed;
    }

    /**
     * Deletes a task by its index from the task list.
     * 
     * @param taskList The task list.
     * @param storage The storage to save the updated task list.
     * @return The deleted task.
     * @throws PookieException If the index is invalid or not a number.
     */
    private Task deleteTaskByIndex(TaskList taskList, Storage storage) throws PookieException {
        try {
            int index = Integer.parseInt(args[1]) - 1;
            Task removed = taskList.deleteTask(index);
            saveTaskList(taskList, storage);
            return removed;
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index provided is not a number! >w<!");
        } catch (IndexOutOfBoundsException e) {
            throw new PookieException("Owo? That task index doesn't exist! >w<!");
        }
    }

}
