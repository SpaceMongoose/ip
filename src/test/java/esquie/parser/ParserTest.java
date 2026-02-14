package esquie.parser;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import esquie.commands.AddCommand;
import esquie.commands.DeleteCommand;
import esquie.commands.ExitCommand;
import esquie.commands.ListCommand;
import esquie.commands.MarkCommand;
import esquie.exceptions.EsquieException;

public class ParserTest {
    @Test
    public void parseList_returnsListCommand() throws Exception {
        assertTrue(Parser.parse("list") instanceof ListCommand);
        assertTrue(Parser.parse("LIST") instanceof ListCommand);
    }

    @Test
    public void parseBye_returnsExitCommand() throws Exception {
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
        assertInstanceOf(ExitCommand.class, Parser.parse("BYE"));
    }

    @Test
    public void parseTodo_returnsAddCommand() throws Exception {
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
        assertInstanceOf(AddCommand.class, Parser.parse("TODO read book"));
    }

    @Test
    public void parseDeadline_returnsAddCommand() throws Exception {
        assertInstanceOf(AddCommand.class, Parser.parse("deadline read book /by 2026-01-01"));
        assertInstanceOf(AddCommand.class, Parser.parse("DEADLINE read book /by 2026-01-01"));
    }

    @Test
    public void parseEvent_returnsAddCommand() throws Exception {
        assertInstanceOf(AddCommand.class, Parser.parse("event read book /from 2026-01-01 /to 2026-01-02"));
        assertInstanceOf(AddCommand.class, Parser.parse("EVENT read book /from 2026-01-01 /to 2026-01-02"));
    }

    @Test
    public void parseDelete_returnsDeleteCommand() throws Exception {
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parse("DELETE 1"));
    }

    @Test
    public void parseMarkUnmark_returnsMarkCommand() throws Exception {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("MARK 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("unmark 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("UNMARK 1"));
    }

    @Test
    public void parseUnknownCommand_throwsException() throws Exception {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("this is a random command");
        });
        assertTrue(e.getMessage().contains("Whoopsie! Esquie did not understand that!"));
    }

    @Test
    public void parseTodo_missingArg_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("todo"); // Missing description
        });
        assertTrue(e.getMessage().contains("Something is missing from the todo command!"));
    }

    @Test
    public void parseDeadline_missingBy_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("deadline read book"); // Missing /by
        });

        EsquieException eInvalidDate = assertThrows(EsquieException.class, () -> {
            Parser.parse("deadline read book /by tomorrow"); // Improper date
        });

        assertTrue(e.getMessage().contains("Something is missing from the deadline command!"));
        assertTrue(eInvalidDate.getMessage().contains("Please enter the date in yyyy-MM-dd"));
    }

    @Test
    public void parseEvent_missingBy_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("event read book /to 2026-01-01"); // Missing /from
        });

        EsquieException eInvalidDate = assertThrows(EsquieException.class, () -> {
            Parser.parse("event read book /from 2026-01-01"); // Missing /to
        });

        assertTrue(e.getMessage().contains("Either a task description or time is missing!"));
        assertTrue(eInvalidDate.getMessage().contains(
                "Either the from or to timing is missing from the event command!"));
    }


    @Test
    public void parseDelete_invalidNumber_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("delete abc"); // Not a Number
        });

        EsquieException eNoNumber = assertThrows(EsquieException.class, () -> {
            Parser.parse("delete"); // Missing Number
        });

        assertTrue(e.getMessage().contains("Whoopsie! You did not give me a proper number!"));
        assertTrue(eNoNumber.getMessage().contains("Something is wrong with the delete command!"));
    }

    @Test
    public void parseMark_invalidNumber_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("mark abc"); // Not a Number
        });

        EsquieException eNoNumber = assertThrows(EsquieException.class, () -> {
            Parser.parse("mark"); // Missing Number
        });

        assertTrue(e.getMessage().contains("Whoopsie!"));
        assertTrue(eNoNumber.getMessage().contains("Whoopsie! Command is missing an argument!"));
    }
}
