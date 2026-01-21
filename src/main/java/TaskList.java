public class TaskList {

    private static final Task[] tasks = new Task[100];
    private static int taskCount = 0;

    public static void addTask(String task) {

        if (taskCount >= tasks.length) {
            throw new IllegalStateException("UwU! Task list is full! Cannot add more tasks.");
        }

        tasks[taskCount] = new Task(task);
        taskCount++;
    }

    public static int getTaskCount() {
        return taskCount;
    }

    public static Task getTask(int index) {
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException("That's not a valid index dummy! -w-");
        }
        return tasks[index];
    }
    
}
