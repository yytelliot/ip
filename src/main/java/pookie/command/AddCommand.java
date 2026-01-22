package pookie.command;

import java.util.Arrays;
import pookie.task.DeadlineTask;
import pookie.task.EventTask;
import pookie.task.Task;
import pookie.task.TaskList;
import pookie.task.TodoTask;

/**
 * Command to add a new task to the task list.
 */
public class AddCommand extends Command {

    private final String[] args;

    public AddCommand(String[] args) {
        this.args = args;
    }

    private static int findToken(String[] arr, int start, String token) {
        for (int i = start; i < arr.length; i++) {
            if (arr[i].equals(token)) return i;
        }
        return -1;
    }

    private static String join(String[] arr, int start, int endExclusive) {
        if (start >= endExclusive) return "";
        return String.join(" ", Arrays.copyOfRange(arr, start, endExclusive)).trim();
    }

    @Override
    public String execute() {

        if (args.length < 2) {
            return "Please provide a task to add! >w<";
        }

        String taskType = args[0];

        Task task;

        switch (taskType) {
            case "todo" -> {
                String description = join(args, 1, args.length);
                if (description.isEmpty()) {
                    return "The description of a todo cannot be empty! >w<";
                }
                task = new TodoTask(description);
            }
            case "deadline" -> {
                int byIdx = findToken(args, 1, "/by");
                if (byIdx == -1) {
                    return "Please provide a description and deadline using '/by'! >w<";
                }

                String taskDescription = join(args, 1, byIdx);
                String byTime = join(args, byIdx + 1, args.length);

                if (taskDescription.isEmpty() || byTime.isEmpty()) {
                    return "The description or deadline of the deadline cannot be empty! >w<";
                }

                task = new DeadlineTask(taskDescription, byTime);
            }
            case "event" -> {
                int fromIdx = findToken(args, 1, "/from");
                int toIdx = findToken(args, 1, "/to");

                if (fromIdx == -1 || toIdx == -1 || fromIdx >= toIdx) {
                    return "Please provide event description and time using '/from' and '/to'! >w<";
                }

                String eventDescription = join(args, 1, fromIdx);
                String fromTime = join(args, fromIdx + 1, toIdx);
                String toTime = join(args, toIdx + 1, args.length);

                if (eventDescription.isEmpty() || fromTime.isEmpty() || toTime.isEmpty()) {
                    return "The description, from time, and to time of the event cannot be empty! >w<";
                }

                task = new EventTask(eventDescription, fromTime, toTime);
            }
            default -> {
                return "I don't know the task: " + taskType + ". Please use 'todo', 'deadline', or 'event'. ;w;";
            }
        }

        // add task to task list
        try {
            TaskList.addTask(task);
        } catch (IllegalStateException e) {
            return e.getMessage();
        }
        
        // print message
        return "Added task: " + task.getDescription() + " ^w^";
    }
}
