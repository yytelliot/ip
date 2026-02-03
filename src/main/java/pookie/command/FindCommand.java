package pookie.command;

import pookie.task.TaskList;

/**
 * Command to find all tasks containing a specific keyword.
 */
public class FindCommand extends Command{

    private final String keyword;

    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String execute(TaskList taskList, pookie.storage.Storage storage) {
        StringBuilder sb = new StringBuilder();
        sb.append("Pookie found some tasks! :3\n");
        int count = 1;
        for (var task : taskList.findTasks(keyword)) {
            sb.append(count).append(". ").append(task.toString()).append("\n");
            count++;
        }
        return sb.toString().trim();
    }
    
}
