package pookie.task;

import java.time.LocalDate;
import pookie.format.Formats;

/**
 * Represents a Deadline task.
 */
public class DeadlineTask extends Task {
    private final LocalDate byDate;

    public DeadlineTask(String description, LocalDate byDate) {
        super(description);

        this.byDate = byDate;
    }

    public LocalDate getByTime() {
        return this.byDate;
    }

    @Override
    public String getType() {
        return "D";
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + byDate.format(Formats.DISPLAY_DATE) + ")";
    }
}