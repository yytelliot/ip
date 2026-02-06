package pookie.command;

import pookie.storage.Storage;
import pookie.task.TaskList;

/**
 * Command to list all tasks in the task list.
 */
public class ListCommand extends Command {

    @Override
    public String execute(TaskList taskList, Storage storage) {
        StringBuilder sb = new StringBuilder("Here are the tasks in your list :3\n");
        for (int i = 0; i < taskList.getTaskCount(); i++) {
            sb.append((i + 1)).append(". ")
                    .append(taskList.getTask(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }

}
