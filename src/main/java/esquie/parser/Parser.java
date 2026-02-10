package esquie.parser;

import java.time.format.DateTimeParseException;

import esquie.commands.AddCommand;
import esquie.commands.Command;
import esquie.commands.DeleteCommand;
import esquie.commands.ExitCommand;
import esquie.commands.FindCommand;
import esquie.commands.ListCommand;
import esquie.commands.MarkCommand;
import esquie.exceptions.EsquieException;
import esquie.tasks.Deadline;
import esquie.tasks.Event;
import esquie.tasks.Task;
import esquie.tasks.Todo;

/**
 * Parser that processes input, and returns a Command Object to perform the respective action.
 */
public class Parser {

    /**
     * Parses the command into 2, the command and the arguments.
     * @param command is the command input by the user.
     * @return Command object.
     */
    public static Command parse(String command) throws EsquieException {
        String[] input = command.split(" ", 2);
        // If incomplete string, let it be ""
        String arguments = input.length > 1 ? input[1] : "";

        switch (input[0].toUpperCase().trim()) {
        case "LIST" -> {
            return new ListCommand();
        }
        case "BYE" -> {
            return new ExitCommand();
        }
        case "TODO" -> {
            return parseTodo(arguments);
        }
        case "DEADLINE" -> {
            return parseDeadline(arguments);
        }
        case "EVENT" -> {
            return parseEvent(arguments);
        }
        case "DELETE" -> {
            return parseDelete(arguments);
        }
        case "MARK", "UNMARK" -> {
            return parseMark(input[0].toUpperCase(), arguments);
        }
        case "FIND" -> {
            return parseFind(arguments);
        }
        default -> {
            throw new EsquieException("Oopsie! Esquie did not understand that!");
        }
        }
    }

    /**
     * Parses the remainder of the input to return a AddCommand object.
     * @param input is the remainder of the command not processed.
     * @return AddCommand object.
     */
    private static Command parseTodo(String input) throws EsquieException {
        if (input.trim().isEmpty()) {
            throw new EsquieException("Whoopsie! Something is missing from the todo command!"
                    + "\n" + "Example Usage: todo borrow book");
        }

        return new AddCommand(new Todo(input.trim()));
    }

    /**
     * Parses the remainder of the input to return a AddCommand object.
     * @param input is the remainder of the command not processed.
     * @return AddCommand object.
     */
    private static Command parseDeadline(String input) throws EsquieException {
        // e.g. return book /by 2026-01-01
        String[] byInput = input.split(" /by ", 2);

        if (byInput.length < 2 || byInput[0].trim().isEmpty() || byInput[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie! Something is missing from the deadline command!"
                    + "\n" + "Example Usage: deadline Play E33 /by 2026-01-25 1750"
                    + "\n" + "Example Usage: deadline Play E33 /by 2026-01-25");
        }

        assert byInput.length == 2 : "parseDeadline split logic fails";

        try {
            Task task = new Deadline(byInput[0].trim(), byInput[1].trim());
            return new AddCommand(task);
        } catch (DateTimeParseException e) {
            throw new EsquieException("Oopsie! Please enter the date in yyyy-MM-dd or yyyy-MM-dd HHmm format!"
                    + "\n"
                    + "Example Usage: /by 2026-01-25 OR /by 2026-01-25 1750");
        }
    }

    /**
     * Parses the remainder of the input to return a AddCommand object.
     * @param input is the remainder of the command not processed.
     * @return AddCommand object.
     */
    private static Command parseEvent(String input) throws EsquieException {
        // This splits the description and time
        String[] splitFrom = input.split(" /from ", 2);
        if (splitFrom.length < 2 || splitFrom[0].trim().isEmpty() || splitFrom[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie, something is wrong with the event command! \n"
                    + "Either a task description or time is missing!"
                    + "\n"
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n"
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }

        assert splitFrom.length == 2 : "parseEvent split logic fails";

        String description = splitFrom[0];
        String date = splitFrom[1];

        // This further splits the time to obtain from and to
        String[] splitTo = date.split(" /to ", 2);
        if (splitTo.length < 2 || splitTo[0].trim().isEmpty() || splitTo[1].trim().isEmpty()) {
            throw new EsquieException("Whoopsie, something is wrong with the event command!\n"
                    + "Either the from or to timing is missing from the event command!"
                    + "\n"
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n"
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }

        assert splitTo.length == 2 : "parseEvent split time logic fails";

        try {
            Task task = new Event(description.trim(), splitTo[0].trim(), splitTo[1].trim());
            return new AddCommand(task);
        } catch (DateTimeParseException e) {
            throw new EsquieException("Oopsie! Please enter the date in yyyy-MM-dd or yyyy-MM-dd HHmm format!"
                    + "\n"
                    + "Example Usage: event Play E33 /from 2026-01-25 1300 /to 2026-01-25 1800"
                    + "\n"
                    + "Example Usage: event Play E33 /from 2026-01-25 /to 2026-01-25");
        }
    }

    /**
     * Parses the remainder of the input to return a DeleteCommand object.
     * @param input is the remainder of the command not processed.
     * @return DeleteCommand object.
     */
    private static Command parseDelete(String input) throws EsquieException {
        if (input.trim().isEmpty()) {
            throw new EsquieException("Whoopsie! Something is wrong with the delete command!"
                    + "\n" + "Example Usage: delete 3");
        }
        try {
            int index = Integer.parseInt(input.trim()) - 1;
            return new DeleteCommand(index);
        } catch (NumberFormatException e) {
            throw new EsquieException("Whoopsie! You did not give me a proper number!"
                    + "\n" + "Example Usage: delete 3");
        }
    }

    /**
     * Parses the remainder of the input to return a MarkCommand object.
     * @param command is the initial command e.g. mark or unmark.
     * @param input is the remainder of the command not processed.
     * @return MarkCommand object.
     */
    private static Command parseMark(String command, String input) throws EsquieException {
        // Error Checking
        // If the 2nd input is ""
        if (input.trim().isEmpty()) {
            throw new EsquieException("Whoopsie! command is missing an argument!"
                    + "\n" + "Example Usage: mark 1 OR unmark 1");
        }

        try {
            // Checks if the 2nd number (input) is Integer or not
            // Integer.parseInt returns NumberFormatException if fails
            int taskNumber = Integer.parseInt(input.trim()) - 1;
            boolean isMark = command.equalsIgnoreCase("mark");
            return new MarkCommand(taskNumber, isMark);
        } catch (NumberFormatException e) {
            throw new EsquieException("You didnt give me a number... Esquie is now sad :("
                    + "\n" + "Example Usage: mark 1 OR unmark 1");

        }
    }

    /**
     * Parses the remainder of the input to return a FindCommand object.
     * @param input is the remainder of the command not processed.
     * @return FindCommand object.
     */
    private static Command parseFind(String input) throws EsquieException {
        if (input.trim().isEmpty()) {
            throw new EsquieException("Whoopsie! You have to tell me what to find!"
                    + "\n" + "Example Usage: find book");
        }

        return new FindCommand(input.trim().toLowerCase());
    }
}
