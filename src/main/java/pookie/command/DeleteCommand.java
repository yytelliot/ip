package pookie.command;

import pookie.exception.PookieException;
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
    public String execute() throws PookieException {

        if (args.length < 2) {
            throw new PookieException(">w<! Please provide the index of the task to delete!");
        }

        int index;
        try {
            index = Integer.parseInt(args[1]) - 1;
        } catch (NumberFormatException e) {
            throw new PookieException("Owo? The index provided is not a number! >w<!");
        }

        try {
            Task removed = TaskList.deleteTask(index); // make this return Task
            return "I've deleted this task! >:3\n  " + removed;
        } catch (IndexOutOfBoundsException e) {
            throw new PookieException("Owo? That task index doesn't exist! >w<!");
        }
    }
    
}
