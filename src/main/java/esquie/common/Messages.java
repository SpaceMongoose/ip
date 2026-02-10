package esquie.common;

/**
 * Class for user-visible messages.
 */
public class Messages {

    // ERR MESSAGE
    public static final String ERR_TASKLIST_FULL = "Whoopsie! Number of tasks is full!";
    public static final String ERR_TASK_NOT_EXIST = "Whoopsie! This task does not exist";
    public static final String ERR_TASK_NOT_FOUND = "Whoopsie! Nothing could be found";

    // SUCCESS MESSAGE
    public static final String MSG_TASK_ADD = "Got it. I've added this task:";
    public static final String MSG_TASK_DELETE = "Got it. I've removed this task:";
    public static final String MSG_TASK_LIST = "Listing Current Tasks:";
    public static final String MSG_TASK_FIND = "Here are the matching tasks in your list:";
    public static final String MSG_TASK_MARK = "WhooWhee! I've marked this task as done:";
    public static final String MSG_TASK_UNMARK = "WhooWhee! I've marked this task as not done yet:";

    // GENERAL ERROR MESSAGE
    public static final String ERR_COMMAND_NOT_UNDERSTOOD = "Whoopsie! Esquie did not understand that!";

    // TODO ERROR MESSAGE
    public static final String ERR_TODO_FORMAT = "Whoopsie! Something is missing from the todo command!"
            + "\n" + "Example Usage: todo borrow book";

    // DEADLINE ERROR MESSAGE
    public static final String ERR_DEADLINE_FORMAT = "Whoopsie! Something is missing from the deadline command!"
            + "\n" + "Example Usage: deadline Play E33 /by 2026-01-25 1750"
            + "\n" + "Example Usage: deadline Play E33 /by 2026-01-25";

    public static final String ERR_DEADLINE_DATE = "Oopsie! Please enter the date in yyyy-MM-dd or yyyy-MM-dd HHmm "
            + "format!" + "\n" + "Example Usage: /by 2026-01-25 OR /by 2026-01-25 1750";

    // EVENT ERROR MESSAGE
    private static final String ERR_EVENT_EXAMPLE = "Example Usage: event Play E33 /from 2026-01-25 1300 "
            + "/to 2026-01-25 1800"
            + "\n"
            + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25";

    public static final String ERR_EVENT_FORMAT = "Whoopsie, something is wrong with the event command!"
            + "\n" + "Either a task description or time is missing!" + "\n"
            + ERR_EVENT_EXAMPLE;

    public static final String ERR_EVENT_TIMING = "Whoopsie, something is wrong with the event command!"
            + "\n" + "Either the from or to timing is missing from the event command!" + "\n"
            + ERR_EVENT_EXAMPLE;

    public static final String ERR_EVENT_DATE = "Oopsie! Please enter the date in yyyy-MM-dd or yyyy-MM-dd HHmm format!"
            + "\n"
            + ERR_EVENT_EXAMPLE;

    // DELETE ERROR MESSAGE
    private static final String ERR_DELETE_EXAMPLE = "Example Usage: delete 3";
    public static final String ERR_DELETE_FORMAT = "Whoopsie! Something is wrong with the delete command!"
            + "\n"
            + ERR_DELETE_EXAMPLE;

    public static final String ERR_DELETE_NUMBER = "Whoopsie! You did not give me a proper number!"
            + "\n"
            + ERR_DELETE_EXAMPLE;

    // MARK ERROR MESSAGE
    private static final String ERR_MARK_EXAMPLE = "Example Usage: mark 1 OR unmark 1";
    public static final String ERR_MARK_FORMAT = "Whoopsie! command is missing an argument!"
            + "\n"
            + ERR_MARK_EXAMPLE;
    public static final String ERR_MARK_NUMBER = "You didnt give me a number... Esquie is now sad :("
            + "\n"
            + ERR_MARK_EXAMPLE;

    // FIND ERROR MESSAGE
    private static final String ERR_FIND_EXAMPLE = "Example Usage: find book";
    public static final String ERR_FIND_FORMAT = "Whoopsie! You have to tell me what to find!"
            + "\n"
            + ERR_FIND_EXAMPLE;


    private Messages() {};

    public static String getTaskCountMessage(int size) {
        return "Now you have " + size + " tasks in the list.";
    }
}
