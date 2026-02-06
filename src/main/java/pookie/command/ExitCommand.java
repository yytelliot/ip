package pookie.command;

import pookie.storage.Storage;
import pookie.task.TaskList;

/**
 * Command to exit the application.
 */
public class ExitCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage) {
        return "Bye bye! TwT";
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
