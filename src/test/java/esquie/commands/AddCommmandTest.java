package esquie.commands;

import esquie.tasks.TaskList;
import esquie.tasks.Todo;
import esquie.ui.Ui;
import esquie.storage.Storage;
import esquie.exceptions.EsquieException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

public class AddCommmandTest {

    @TempDir
    Path tempDir;

    @Test
    public void execute_validAdd() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create the Task to add
        Todo todoTask = new Todo("Read book");
        AddCommand command = new AddCommand(todoTask);

        // 3. Execute
        command.execute(taskList, ui, storage);

        // 4. Assertions
        assertEquals(1, taskList.size());
        assertInstanceOf(Todo.class, taskList.get(0));
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(1, lines.size());
        assertEquals("T | 0 | Read book", lines.get(0));
    }

    @Test
    public void execute_invalidAdd() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create the Task to add
        Todo todoTask = new Todo("Read book");
        AddCommand command = new AddCommand(todoTask);

        // 3. Populate current taskList with mock tasks
        for(int i = 0; i < 100; i++) {
            taskList.add(new Todo("Read " + i));
        }

        // 4. Execute the command
        EsquieException e = assertThrows(EsquieException.class, () -> {
            command.execute(taskList, ui, storage);
        });
        assertEquals("Whoopsie! Number of tasks is full!", e.getMessage());

    }
}
