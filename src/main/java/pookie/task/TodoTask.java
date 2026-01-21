package pookie.task;

/**
 * Represents a Todo task.
 */
public class TodoTask extends Task {

    public TodoTask(String description) {
        super(description);
    }

    @Override
    public String getTypeIcon() {
        return "[T]";
    }

}