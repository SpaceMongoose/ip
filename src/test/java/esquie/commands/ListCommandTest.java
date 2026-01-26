package esquie.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import esquie.storage.Storage;
import esquie.tasks.Deadline;
import esquie.tasks.TaskList;
import esquie.tasks.Todo;
import esquie.ui.Ui;

public class ListCommandTest {
    @TempDir
    Path tempDir;

    // Variables for holding the tasks listed out
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    // Redirect System.out to the outContent stream
    @BeforeEach
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    // After each test, reset the System.out to System.out
    @AfterEach
    public void clearStream() {
        System.setOut(originalOut);
    }

    @Test
    public void listWith_noCorrupt() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create a few dummy tasks
        taskList.add(new Todo("Read book"));
        taskList.add(new Deadline("Play games", "2026-01-01"));

        ListCommand command = new ListCommand();
        command.execute(taskList, ui, storage);

        String output = outContent.toString();
        assertTrue(output.contains("1.[T][ ] Read book"));
        assertTrue(output.contains("2.[D][ ] Play games (by: 1 Jan 2026)"));

    }
}
