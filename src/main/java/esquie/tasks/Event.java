package esquie.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import esquie.common.Messages;
import esquie.exceptions.EsquieException;

/**
 * Task with a specific date or time attached to it.
 * Has a Start and End date.
 */
public class Event extends Task {
    private static final String DATE_PATTERN_NO_TIME = "d MMM yyyy";
    private static final String DATE_PATTERN_WITH_TIME = "d MMM yyyy, HHmm'H'";
    protected LocalDateTime from;
    protected LocalDateTime to;

    /**
     * Creates a new Event Task.
     *
     * @param description The description of the event.
     * @param from The start time or date of the event.
     * @param to The end time or date of the event.
     */
    public Event(String description, String from, String to) throws EsquieException {
        super(description);
        this.from = LocalDateTime.parse(from, Task.DATE_FORMATTER);
        this.to = LocalDateTime.parse(to, Task.DATE_FORMATTER);
        if (this.to.isBefore(this.from)) {
            throw new EsquieException(Messages.ERR_EVENT_TIME_CONFLICT);
        }
    }

    /**
     * Creates a new Event Task that may or may not be done.
     *
     * @param description The description of the event.
     * @param from The start time or date of the event.
     * @param to The end time or date of the event.
     * @param isDone Indicate if task is marked or not.
     */
    public Event(String description, String from, String to, boolean isDone) throws EsquieException {
        super(description, isDone);
        this.from = LocalDateTime.parse(from, Task.DATE_FORMATTER);
        this.to = LocalDateTime.parse(to, Task.DATE_FORMATTER);
        if (this.to.isBefore(this.from)) {
            throw new EsquieException(Messages.ERR_EVENT_TIME_CONFLICT);
        }
    }


    /**
     * Returns the String representation of an Event task.
     *
     * @return A formatted string [e.g. [E][] Sleep (from: 6 Jun 2026, 1800H to: 7 Jun 2026, 1900H)].
     */
    @Override
    public String toString() {
        boolean noFromTime = (from.getHour() == 0 && from.getMinute() == 0);
        boolean noToTime = (to.getHour() == 0 && to.getMinute() == 0);

        String patternFrom = noFromTime ? DATE_PATTERN_NO_TIME : DATE_PATTERN_WITH_TIME;
        String patternTo = noToTime ? DATE_PATTERN_NO_TIME : DATE_PATTERN_WITH_TIME;

        String formatFrom = from.format(DateTimeFormatter.ofPattern(patternFrom));
        String formatTo = to.format(DateTimeFormatter.ofPattern(patternTo));
        return "[E]" + super.toString() + " (from: " + formatFrom + " to: " + formatTo + ")";
    }

    /**
     * Returns a standardized string for task saving.
     * @return "E | 0 | Play E33 | 2000-01-01 1300 | 2000-01-01 1800" OR
     *         "E | 0 | Play E33 | 2000-01-01 0000 | 2000-01-01 0000" (If no time is specified)
     */
    @Override
    public String saveString() {
        return "E" + " | " + super.saveString()
                + " | " + from.format(Task.SAVE_FORMATTER)
                + " | " + to.format(Task.SAVE_FORMATTER);
    }
}
