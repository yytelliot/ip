package pookie.command;

import pookie.task.TaskList;

/**
 * Command to find all tasks containing a specific keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList taskList, pookie.storage.Storage storage) {
        pookie.task.TaskList filtered = new pookie.task.TaskList();
        for (var task : taskList.findTasks(keyword)) {
            filtered.addTask(task);
        }
        if (filtered.getTaskCount() == 0) {
            return "Pookie couldn't find any tasks with that keyword! >w<";
        }
        return formatTaskList(filtered, "Pookie found some tasks! :3");
    }

}
