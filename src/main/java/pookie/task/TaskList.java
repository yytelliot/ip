package pookie.task;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and manages the list of tasks.
 */
public class TaskList {

    private static final List<Task> tasks= new ArrayList<>();

    /**
     * Adds a task to the task list
     * 
     * @param task Task description
     * @throws IllegalStateException if the task list is full
     */
    public static void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Returns the number of tasks in the task list
     * 
     * @return Number of tasks
     */
    public static int getTaskCount() {
        return tasks.size();
    }


    /**
     * Returns the task at the given index
     * 
     * @param index Index of the task to retrieve
     * @return Task at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public static Task getTask(int index) {
        return tasks.get(index);
    }

    /**
     * Deletes the task at the given index
     * 
     * @param index Index of the task to delete
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public static Task deleteTask(int index) {
        Task task = tasks.get(index);
        tasks.remove(index);
        return task;
    }

    /**
     * Clears all tasks from the task list
     */
    public static void clearTasks() {
        tasks.clear();
    }
    
}
