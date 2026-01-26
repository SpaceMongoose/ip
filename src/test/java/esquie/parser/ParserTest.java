package esquie.parser;

import esquie.commands.*;
import esquie.exceptions.EsquieException;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    @Test
    public void parse_list_returnsListCommand() throws Exception {
        assertTrue(Parser.parse("list") instanceof ListCommand);
        assertTrue(Parser.parse("LIST") instanceof ListCommand);
    }

    @Test
    public void parse_bye_returnsExitCommand() throws Exception {
        assertInstanceOf(ExitCommand.class, Parser.parse("bye"));
        assertInstanceOf(ExitCommand.class, Parser.parse("BYE"));
    }

    @Test
    public void parse_todo_returnsAddCommand() throws Exception {
        assertInstanceOf(AddCommand.class, Parser.parse("todo read book"));
        assertInstanceOf(AddCommand.class, Parser.parse("TODO read book"));
    }

    @Test
    public void parse_deadline_returnsAddCommand() throws Exception {
        assertInstanceOf(AddCommand.class, Parser.parse("deadline read book /by 2026-01-01"));
        assertInstanceOf(AddCommand.class, Parser.parse("DEADLINE read book /by 2026-01-01"));
    }

    @Test
    public void parse_event_returnsAddCommand() throws Exception {
        assertInstanceOf(AddCommand.class, Parser.parse("event read book /from 2026-01-01 /to 2026-01-02"));
        assertInstanceOf(AddCommand.class, Parser.parse("EVENT read book /from 2026-01-01 /to 2026-01-02"));
    }

    @Test
    public void parse_delete_returnsDeleteCommand() throws Exception {
        assertInstanceOf(DeleteCommand.class, Parser.parse("delete 1"));
        assertInstanceOf(DeleteCommand.class, Parser.parse("DELETE 1"));
    }

    @Test
    public void parse_mark_unmark_returnsMarkCommand() throws Exception {
        assertInstanceOf(MarkCommand.class, Parser.parse("mark 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("MARK 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("unmark 1"));
        assertInstanceOf(MarkCommand.class, Parser.parse("UNMARK 1"));
    }

    @Test
    public void parse_unknownCommand_throwsException() throws Exception {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("this is a random command");
        });
        assertTrue(e.getMessage().contains("Oopsie! Esquie did not understand that!"));
    }

    @Test
    public void parse_todo_missingArg_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("todo"); // Missing description
        });
        assertTrue(e.getMessage().contains("Something is missing from the todo command!"));
    }

    @Test
    public void parse_deadline_missingBy_throwsException() {
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
    public void parse_event_missingBy_throwsException() {
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
    public void parse_delete_invalidNumber_throwsException() {
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
    public void parse_mark_invalidNumber_throwsException() {
        EsquieException e = assertThrows(EsquieException.class, () -> {
            Parser.parse("mark abc"); // Not a Number
        });

        EsquieException eNoNumber = assertThrows(EsquieException.class, () -> {
            Parser.parse("mark"); // Missing Number
        });

        assertTrue(e.getMessage().contains("You didnt give me a number... Esquie is now sad :("));
        assertTrue(eNoNumber.getMessage().contains("Whoopsie! command is missing an argument!"));
    }
}
