package pookie.task;

/**
 * Represents a generic task with a description and completion status.
 */
public abstract class Task {

    private final String description;
    private boolean isDone;

    /**
     * Constructs a Task with the given description.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null : "Task description must not be null";
        this.description = description;
        this.isDone = false;
    }

    public boolean isDone() {
        return isDone;
    }

    public abstract String getType();

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsUndone() {
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        String statusIcon = isDone() ? "[X]" : "[ ]";
        return "[" + getType() + "]" + statusIcon + " " + description;
    }
}
