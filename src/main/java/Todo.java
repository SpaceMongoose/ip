/**
 * Task without any specific date or time attached to it.
 */
public class Todo extends Task {

    /**
     * Creates a new Todo Task.
     *
     * @param description The description of the event.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the String representation of an Todo task.
     *
     * @return A formatted string [e.g. [T][ ] borrow book].
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
