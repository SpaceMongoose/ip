package esquie.storage;

import esquie.tasks.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class StorageTest {

    @TempDir
    Path tempDir;

    @Test
    public void write_task_to_saveFile() throws Exception {
        // tempFile -> tempDir/temp_esquie.txt

        // 1. Set the path and simulate verification
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();

        // 2. Create new Task
        Todo todoTask = new Todo("Read book");
        storage.writeTask(todoTask);

        // 3. Verify
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(1, lines.size());
        assertEquals("T | 0 | Read book", lines.get(0));

    }

    @Test
    public void load_task_to_taskList() throws Exception {
        // 1. Set the path and simulate verification
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();

        // 2. Simulate adding the tasks
        Todo todoTask = new Todo("Read book");
        Deadline deadlineTask = new Deadline("Read book", "2026-01-01");
        Event eventTask = new Event("Read book", "2026-01-01", "2026-01-02");
        storage.writeTask(todoTask);
        storage.writeTask(deadlineTask);
        storage.writeTask(eventTask);

        // 3. Load from save file to tasklist
        ArrayList<Task> taskList = storage.loadTasks();

        // 4. Assertions
        assertEquals(3, taskList.size());
        assertInstanceOf(Todo.class, taskList.get(0));
        assertInstanceOf(Deadline.class, taskList.get(1));
        assertInstanceOf(Event.class, taskList.get(2));
    }

    @Test
    public void load_task_skip_corruptLines() throws Exception {
        // 1. Set the path and simulate verification
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();

        // 2. Add Valid and Invalid Tasks
        Files.write(tempFile, "T | 0 | Read book\nD tae dfad poop\nD | 0 | Play E33 | 2026-01-01 0000".getBytes());

        // 3. Test this save file
        ArrayList<Task> taskList = storage.loadTasks();
        assertEquals(2, taskList.size());
        assertInstanceOf(Todo.class, taskList.get(0));
        assertInstanceOf(Deadline.class, taskList.get(1));
    }

    @Test
    public void overwriteAll_updateSave() throws Exception {
        // 1. Set the path and simulate old tasks
        Path tempFile = tempDir.resolve("temp_esquie.txt");
        Storage storage = new Storage(tempFile.toString());
        storage.checkSave();
        Files.write(tempFile, "T | 0 | Read book".getBytes());

        // 2. Create new tasks to overwrite with
        TaskList taskList = new TaskList();
        taskList.add(new Todo("Play E33"));
        taskList.add(new Deadline("Play BG3", "2026-01-01"));

        // 3. Overwrite the current list
        storage.overwriteAll(taskList);

        // 4. Assertions
        List<String> lines = Files.readAllLines(tempFile);
        assertEquals(2, lines.size());
        assertEquals("T | 0 | Play E33", lines.get(0));
        assertEquals("D | 0 | Play BG3 | 2026-01-01 0000", lines.get(1));
    }
}
