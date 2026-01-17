/**
 * Task with a specific date or time attached to it.
 * Has a Start and End date.
 */
public class Event extends Task {
    protected String from;
    protected String to;

    /**
     * Creates a new Event Task.
     *
     * @param description The description of the event.
     * @param from The start time or date of the event.
     * @param to The end time or date of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }


    /**
     * Returns the String representation of an Event task.
     *
     * @return A formatted string [e.g. [E][] Sleep (from: Mon 2pm to: 4pm)].
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
