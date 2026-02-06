package pookie.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and manages the list of tasks.
 */
public class TaskList {

    private final List<Task> tasks = new ArrayList<>();

    /**
     * Adds a task to the task list
     *
     * @param task Task description
     * @throws IllegalStateException if the task list is full
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the number of tasks in the task list
     *
     * @return Number of tasks
     */
    public int getTaskCount() {
        return tasks.size();
    }

    /**
     * Returns the task at the given index
     *
     * @param index Index of the task to retrieve
     * @return Task at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Finds tasks that contain the given keyword in their description
     *
     * @param keyword Keyword to search for
     * @return List of tasks that match the keyword
     */
    public List<Task> findTasks(String keyword) {
        List<Task> foundTasks = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                foundTasks.add(task);
            }
        }
        return foundTasks;
    }

    /**
     * Deletes the task at the given index
     *
     * @param index Index of the task to delete
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public Task deleteTask(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        return task;
    }

    /**
     * Clears all tasks from the task list
     */
    public void clearTasks() {
        tasks.clear();
    }

}
