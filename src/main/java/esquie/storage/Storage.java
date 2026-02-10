package esquie.storage;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import esquie.common.Messages;
import esquie.exceptions.EsquieException;
import esquie.tasks.Deadline;
import esquie.tasks.Event;
import esquie.tasks.Task;
import esquie.tasks.TaskList;
import esquie.tasks.Todo;

/**
 * Storage deals with loading tasks from the file and saving tasks in the file (./data/esquie.txt).
 */
public class Storage {
    private static final String DELIMITER = " \\| ";
    private final Path filePath;


    /**
     * Initializes a Storage object with a filePath
     * @param filePath is the filePath to the save file (i.e. where the tasks are stored)
     */
    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks into taskList that was from a previous session (if exists).
     *
     * @return a ArrayList of Task objects for creating TaskList class to create a new TaskList, or import old tasks.
     */
    public ArrayList<Task> loadTasks() throws EsquieException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line: lines) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseString(line);
                    tasks.add(task);
                } catch (EsquieException | IndexOutOfBoundsException | DateTimeParseException e) {
                    System.out.println(Messages.ERR_STORAGE_CORRUPT_SKIP + line);
                }
            }
        } catch (IOException e) {
            throw new EsquieException(Messages.ERR_STORAGE_IMPORT);
        }

        return tasks;
    }

    /**
     * Checks if save file (./data/esquie.txt) exists. Otherwise, create.
     */
    public void checkSave() throws EsquieException {
        try {
            // 1. Create directory if missing
            if (filePath.getParent() != null) {
                Files.createDirectories(filePath.getParent());
            }

            // 2. Create esquie.txt (save file) if missing
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
            }
        } catch (IOException e) {
            throw new EsquieException(Messages.ERR_STORAGE_SAVE);
        }

    }

    /**
     * Parses a line of save file format, returns a task object based on this
     *
     * @param input is a line from the save file (esquie.txt).
     */
    private Task parseString(String input) throws EsquieException {
        String[] splitInput = input.split(DELIMITER);
        String type = splitInput[0];
        boolean isDone = splitInput[1].equals("1");

        // Return the tasks created
        return switch (type) {
        case "T" -> new Todo(splitInput[2], isDone);
        case "D" -> new Deadline(splitInput[2], splitInput[3], isDone);
        case "E" -> new Event(splitInput[2], splitInput[3], splitInput[4], isDone);
        default -> throw new EsquieException(Messages.ERR_STORAGE_CORRUPT_FILE);
        };
    }

    /**
     * Append task to esquie.txt.
     * @param task task object of either Todo, Deadline, Event
     */
    public void writeTask(Task task) throws EsquieException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.APPEND)) {
            writer.write(task.saveString());
            writer.newLine();
        } catch (IOException e) {
            throw new EsquieException(Messages.ERR_STORAGE_WRITE);
        }
    }

    /**
     * Overwrites the entire save file whenever there is a mark/unmark/delete event.
     * @param taskList is the task list to modify.
     */
    public void overwriteAll(TaskList taskList) throws EsquieException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            for (int i = 0; i < taskList.size(); i++) {
                writer.write(taskList.get(i).saveString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new EsquieException(Messages.ERR_STORAGE_WRITE);
        }
    }
}
