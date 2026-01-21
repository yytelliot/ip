package pookie.task;

/**
 * Stores and manages the list of tasks.
 */
public class TaskList {

    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    /**
     * Adds a task to the task list
     * 
     * @param task Task description
     * @throws IllegalStateException if the task list is full
     */
    public static void addTask(Task task) {

        if (taskCount >= tasks.length) {
            throw new IllegalStateException("UwU! Task list is full! Cannot add more tasks.");
        }

        tasks[taskCount] = task;
        taskCount++;
    }

    public static int getTaskCount() {
        return taskCount;
    }


    /**
     * Returns the task at the given index
     * 
     * @param index Index of the task to retrieve
     * @return Task at the given index
     * @throws IndexOutOfBoundsException if the index is out of bounds
     */
    public static Task getTask(int index) {
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException("That's not a valid index dummy! -w-");
        }
        return tasks[index];
    }
    
}
