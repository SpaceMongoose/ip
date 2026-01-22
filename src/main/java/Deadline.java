/**
 * Task with a specific date or time attached to it. (Only End Date)
 */
public class Deadline extends Task {
    protected String by;

    /**
     * Creates a new Deadline Task.
     *
     * @param description The description of the event.
     * @param by The deadline of the event.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the String representation of an Deadline task.
     *
     * @return A formatted string [e.g. [D][ ] return book (by: Sunday)].
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }

    /**
     * Returns a standardized string for task saving.
     *
     */
    @Override
    public String saveString() {
        return "D" + " | " + super.saveString()  + " | " + by;
    }
}
