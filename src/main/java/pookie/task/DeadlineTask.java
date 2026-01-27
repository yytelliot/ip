package pookie.task;

/**
 * Represents a Deadline task.
 */
public class DeadlineTask extends Task {
    private final String time;

    public DeadlineTask(String description, String time) {
        super(description);
        this.time = time;
    }

    public String getByTime() {
        return time;
    }

    @Override
    public String getTypeIcon() {
        return "[D]";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + time + ")";
    }
}