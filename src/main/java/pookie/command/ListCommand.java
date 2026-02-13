package pookie.command;

import pookie.storage.Storage;
import pookie.task.TaskList;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage) {
        return formatTaskList(taskList, "Here are the tasks in your list :3");
    }

}
