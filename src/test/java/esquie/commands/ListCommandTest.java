package esquie.commands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

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

        String output = ui.getResponse();
        assertTrue(output.contains("1.[T][ ] Read book"));
        assertTrue(output.contains("2.[D][ ] Play games (by: 1 Jan 2026)"));

    }
}
