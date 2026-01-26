package esquie.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.TaskList;
import esquie.tasks.Todo;
import esquie.ui.Ui;

public class MarkCommandTest {
    @TempDir
    Path tempDir;

    @Test
    public void execute_validMark() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create the Task to add
        Todo todoTask = new Todo("Read book");
        taskList.add(todoTask);

        // 3. Create the MarkCommand
        // Note: Parser handles the -1 already
        MarkCommand commandMark = new MarkCommand(0, true);
        commandMark.execute(taskList, ui, storage);

        // 4. Assertions
        assertEquals(1, taskList.size());
        assertEquals("X", taskList.get(0).getStatusIcon());
        MarkCommand commandUnmark = new MarkCommand(0, false);
        commandUnmark.execute(taskList, ui, storage);
        assertEquals(" ", taskList.get(0).getStatusIcon());
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals("T | 0 | Read book", lines.get(0));
    }

    @Test
    public void execute_invalidMark() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create the Task to add
        Todo todoTask = new Todo("Read book");
        taskList.add(todoTask);

        // 3. Create the MarkCommand
        MarkCommand commandMark = new MarkCommand(2, true);

        // 4. Execute the command
        EsquieException e = assertThrows(EsquieException.class, () -> {
            commandMark.execute(taskList, ui, storage);
        });
        assertEquals("Whoopsie! This task does not exist", e.getMessage());
    }
}
