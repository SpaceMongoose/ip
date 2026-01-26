package esquie.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Task with a specific date or time attached to it. (Only End Date)
 */
public class Deadline extends Task {
    protected LocalDateTime by;

    /**
     * Creates a new Deadline Task.
     *
     * @param description The description of the event.
     * @param by The deadline of the event. Expects a string in "yyyy-MM-dd HH:mm", (HH:mm) is optional
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = LocalDateTime.parse(by, Task.DATE_FORMATTER);
    }

    /**
     * Creates a new Deadline Task that may or may not be done.
     *
     * @param description The description of the event.
     * @param by The deadline of the event. Expects a string in "yyyy-MM-dd HH:mm", (HH:mm) is optional
     * @param isDone Indicate if task is marked or not.
     */
    public Deadline(String description, String by, boolean isDone) {
        super(description, isDone);
        this.by = LocalDateTime.parse(by, Task.DATE_FORMATTER);
    }

    /**
     * Returns the String representation of a Deadline task.
     *
     * @return A formatted string [e.g. [D][ ] return book (by: Jan 15 2026, 1800)].
     */
    @Override
    public String toString() {
        // DATE_FORMATTER returns 00:00 if no time is specified
        // Select which type of pattern (either show time or no time)
        boolean noTime = (by.getHour() == 0 && by.getMinute() == 0);
        String pattern = noTime ? "d MMM yyyy" : "d MMM yyyy, HHmm'H'";
        String formatDate = by.format(DateTimeFormatter.ofPattern(pattern));
        return "[D]" + super.toString() + " (by: " + formatDate + ")";
    }

    /**
     * Returns a standardized string for task saving.
     * e.g. D | 0 | Return Book | 2000-01-01 1300
     * e.g. D | 0 | Return Book | 2000-01-01 0000 (If not time is specified)
     */
    @Override
    public String saveString() {
        return "D" + " | " + super.saveString() + " | " + by.format(Task.SAVE_FORMATTER);
    }
}
