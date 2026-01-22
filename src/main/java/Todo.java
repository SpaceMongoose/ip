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
     * Creates a new Todo Task that may or may not be done.
     *
     * @param description The description of the event.
     * @param isDone Indicate if task is marked or not.
     */
    public Todo(String description, boolean isDone) {
        super(description, isDone);
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

    /**
     * Returns a standardized string for task saving.
     *
     */
    @Override
    public String saveString() {
        return "T" + " | " + super.saveString();
    }
}
