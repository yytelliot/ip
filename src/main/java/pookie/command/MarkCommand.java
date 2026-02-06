package pookie.command;

import java.io.IOException;

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

        // check if index is provided
        if (args.length < 2) {
            throw new PookieException("Please provide the index of the task to mark! >w<");
        }

        Task task;

        try {
            int index = Integer.parseInt(args[1]) - 1;
            task = taskList.getTask(index);
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index provided is not a number! >w<!");
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        task.markAsDone();

        try {
            storage.saveTaskList(taskList);
        } catch (IOException e) {
            throw new PookieException("I couldn't save your tasks. Please try again. >w<");
        }

        return "Nice! Pookie will mark this task as done x3\n  " + task.toString();
    }
}
