package esquie.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.TaskList;
import esquie.tasks.Todo;
import esquie.ui.Ui;

public class FindCommandTest {
    @TempDir
    Path tempDir;


    @Test
    public void execute_find_matchFound() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create a few dummy tasks
        taskList.add(new Todo("Read book"));
        taskList.add(new Todo("Code CS2103"));
        taskList.add(new Todo("Play games"));
        taskList.add(new Todo("Code CS2109S problem set"));
        taskList.add(new Todo("Power nap"));

        FindCommand command = new FindCommand("Code");
        command.execute(taskList, ui, storage);

        String output = ui.getResponse();
        assertTrue(output.contains("1.[T][ ] Code CS2103"));
        assertTrue(output.contains("2.[T][ ] Code CS2109S problem set"));
    }

    @Test
    public void execute_find_matchNotFound() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create a few dummy tasks
        taskList.add(new Todo("Read book"));
        taskList.add(new Todo("Code CS2103"));
        taskList.add(new Todo("Play games"));
        taskList.add(new Todo("Code CS2109S problem set"));
        taskList.add(new Todo("Power nap"));

        FindCommand command = new FindCommand("say");
        Exception e = assertThrows(EsquieException.class, () -> {
            command.execute(taskList, ui, storage);
        });

        assertEquals("Whoopsie! Nothing could be found", e.getMessage());
    }

    @Test
    public void execute_find_matchCaseInsensitive() throws Exception {
        // 1. Create the mock env
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        TaskList taskList = new TaskList();
        Ui ui = new Ui();

        // 2. Create a few dummy tasks
        taskList.add(new Todo("Read book"));
        taskList.add(new Todo("Code CS2103"));
        taskList.add(new Todo("Play games"));
        taskList.add(new Todo("Code CS2109S problem set"));
        taskList.add(new Todo("Power nap"));

        FindCommand command = new FindCommand("code");
        command.execute(taskList, ui, storage);

        String output = ui.getResponse();
        assertTrue(output.contains("1.[T][ ] Code CS2103"));
        assertTrue(output.contains("2.[T][ ] Code CS2109S problem set"));
    }
}
