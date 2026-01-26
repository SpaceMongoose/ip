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

public class DeleteCommandTest {
    @TempDir
    Path tempDir;

    @Test
    public void execute_validDelete() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create the Task to add
        Todo todoTask = new Todo("Read book");
        taskList.add(todoTask);

        // 3. Create the DeleteCommand
        // Note: Parser handles the -1 already
        DeleteCommand command = new DeleteCommand(0);
        command.execute(taskList, ui, storage);

        // 4. Assertions
        assertEquals(0, taskList.size());
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(0, lines.size());
    }

    @Test
    public void execute_invalidDelete() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create the Task to add
        Todo todoTask = new Todo("Read book");
        taskList.add(todoTask);

        // 3. Create the DeleteCommand
        DeleteCommand command = new DeleteCommand(2);

        // 4. Execute the command
        EsquieException e = assertThrows(EsquieException.class, () -> {
            command.execute(taskList, ui, storage);
        });
        assertEquals("Whoopsie! This task does not exist", e.getMessage());
    }
}
