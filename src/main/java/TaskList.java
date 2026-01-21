public class TaskList {

    private static final String[] tasks = new String[100];
    private static int taskCount = 0;

    public static void addTask(String task) throws IllegalStateException {

        if (taskCount >= tasks.length) {
            throw new IllegalStateException("UwU! Task list is full! Cannot add more tasks.");
        }

        tasks[taskCount] = task;
        taskCount++;
    }

    public static int getTaskCount() {
        return taskCount;
    }

    public static String getTask(int index) {
        if (index < 0 || index >= taskCount) {
            throw new IndexOutOfBoundsException("That's not a valid index dummy! -w-");
        }
        return tasks[index];
    }
    
}
