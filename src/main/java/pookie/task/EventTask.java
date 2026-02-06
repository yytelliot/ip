package pookie.task;

import java.time.LocalDate;

import pookie.format.Formats;

/**
 * Represents an Event task.
 */
public class EventTask extends Task {

    private final LocalDate from;
    private final LocalDate to;

    /**
     * Constructs an EventTask with the given description, start date, and end date.
     *
     * @param description The description of the event task.
     * @param from        The start date of the event.
     * @param to          The end date of the event.
     */
    public EventTask(String description, LocalDate from, LocalDate to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDate getFromTime() {
        return from;
    }

    public LocalDate getToTime() {
        return to;
    }

    @Override
    public String getType() {
        return "E";
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + from.format(Formats.DISPLAY_DATE)
                                + " to: " + to.format(Formats.DISPLAY_DATE) + ")";
    }

}
