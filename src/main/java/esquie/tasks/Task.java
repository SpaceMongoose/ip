package esquie.tasks;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;

/**
 * Task class that contains a description and a completion status.
 *
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected static final DateTimeFormatter DATE_FORMATTER = new DateTimeFormatterBuilder()
                                                                .appendPattern("yyyy-MM-dd")
                                                                .optionalStart()
                                                                .appendPattern(" HHmm")
                                                                .optionalEnd()
                                                                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
                                                                .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                                                                .toFormatter();

    protected static final DateTimeFormatter SAVE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    /**
     * Initializes new Task with given description.
     * The initialized task is not done by default (i.e. isDone = false).
     *
     * @param description Text describing the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Initializes new Task with given description and specific "mark" status.
     *
     * @param description Text describing the task.
     * @param isDone Indicate if task is marked or not.
     */
    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Returns the current status of completion in String.
     * "X" indicates completion, " " indicates not complete.
     *
     * @return icon "X" or " ".
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks the task as complete.
     */
    public void markComplete() {
        this.isDone = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markIncomplete() {
        this.isDone = false;
    }

    /**
    * Returns the task description.
    *
    * @return The description of the task.
    */
    public String getDescription() {
        return this.description;
    }


    /**
     * Returns a complete string with both current task status and task description.
     *
     * @return A formatted string (e.g. [] read book or [X] burn book).
     *
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + getDescription();
    }

    /**
     * Returns a standardized string for task saving.
     * @return  A formatted string for saving (e.g. 1 | Read book)
     */
    public String saveString() {
        String statusVal = getStatusIcon().equals("X") ? "1" : "0";
        return statusVal + " | " + getDescription();
    }
}
