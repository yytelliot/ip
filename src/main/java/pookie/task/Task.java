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

    /**
     * Returns the type identifier for this task.
     *
     * @return the task type (e.g., "T" for Todo, "D" for Deadline, "E" for Event)
     */
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

    /**
     * Returns a string representation of the task in the format [Type][Status] Description.
     *
     * @return formatted string representation of the task
     */
    @Override
    public String toString() {
        String statusIcon = isDone() ? "[X]" : "[ ]";
        return "[" + getType() + "]" + statusIcon + " " + description;
    }
}
