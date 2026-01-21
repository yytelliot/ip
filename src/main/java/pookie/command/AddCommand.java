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

    @Override
    public String execute() {

        if (args.length < 1) {
            return "Please provide a task to add! >w<";
        }

        String taskType = args[0];
        String description = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).trim();

        Task task;

        switch (taskType) {
            case "todo" -> {
                if (description.isEmpty()) {
                    return "The description of a todo cannot be empty! >w<";
                }
                task = new TodoTask(description);
            }
            case "deadline" -> {
                String[] parts = description.split(" /by ", 2);

                if (parts.length < 2) {
                    return "Please provide a description and deadline using '/by'! >w<";
                }

                String taskDescription = parts[0].trim();
                String byTime = parts[1].trim();

                if (taskDescription.isEmpty() || byTime.isEmpty()) {
                    return "The description or deadline of the event cannot be empty! >w<";
                }

                task = new DeadlineTask(taskDescription, byTime);
            }
            case "event" -> {
                int fromPos = description.indexOf(" /from ");
                int toPos = description.indexOf(" /to ");
                if (fromPos == -1 || toPos == -1 || fromPos >= toPos) {

                    // // DEBUG: from and to positions
                    // System.out.println(fromPos + " " + toPos);

                    return "Please provide event description and time using '/from' and '/to'! >w<";
                }

                String eventDescription = description.substring(0, fromPos).trim();
                String fromTime = description.substring(fromPos + 7, toPos).trim();
                String toTime = description.substring(toPos + 5).trim();

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
        return "Added task: " + description + " ^w^";
    }
}
