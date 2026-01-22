package pookie.command;

import pookie.exception.PookieException;
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
    public String execute() throws PookieException {

        // check if index is provided
        if (args.length < 2) {
            throw new PookieException("Please provide the index of the task to unmark! >w<");
        }

        Task task;
        try {
            int index = Integer.parseInt(args[1]) - 1;
            task = TaskList.getTask(index);
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index provided is not a number! >w<!");
        } catch (IndexOutOfBoundsException e) {
            return e.getMessage();
        }
        task.markAsUndone();
        return "OK, I've marked this task as not done yet ;w;\n  " + task.toString();
    }
}