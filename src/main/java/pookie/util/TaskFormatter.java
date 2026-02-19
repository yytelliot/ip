package pookie.util;

import java.util.List;

import pookie.task.Task;
import pookie.task.TaskList;

/**
 * Handles formatting of tasks for display.
 */
public class TaskFormatter {

    /**
     * Formats a list of tasks with numbering.
     * Used by commands that display multiple tasks (List, Find).
     *
     * @param taskList the task list to format
     * @param header the header message
     * @return a formatted string with numbered tasks
     */
    public String formatTaskList(TaskList taskList, String header) {
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (int i = 0; i < taskList.getTaskCount(); i++) {
            sb.append((i + 1)).append(". ")
                    .append(taskList.getTask(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }

    /**
     * Formats a list of tasks, displaying each with its original position number from the task list.
     * Used to show which specific tasks were affected by batch operations.
     *
     * @param header the header message to display
     * @param indices the 0-based indices corresponding to each task's original position
     * @param tasks the tasks to display
     * @return a string with the header followed by tasks labeled with their original 1-based positions
     */
    public String formatTasksWithIndices(String header, List<Integer> indices, List<Task> tasks) {
        StringBuilder sb = new StringBuilder(header).append("\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("  ").append(indices.get(i) + 1).append(". ")
                    .append(tasks.get(i))
                    .append("\n");
        }
        return sb.toString().trim();
    }
}
