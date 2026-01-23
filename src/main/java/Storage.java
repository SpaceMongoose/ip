import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * Storage deals with loading tasks from the file and saving tasks in the file (./data/esquie.txt).
 */
public class Storage {
    private final Path filePath;

    public Storage(String filePath) {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Loads tasks into taskList that was from a previous session (if exists).
     * @param taskList is the task list to modify
     */
    void loadTasks(ArrayList<Task> taskList) throws EsquieException {
        if (Files.exists(filePath)) {
            try (BufferedReader reader = Files.newBufferedReader(filePath)) {
                String line = reader.readLine();
                while (line != null) {
                    try {
                        parseString(line, taskList);
                    } catch (EsquieException e) {
                        // No indentation since this loads before Esquie
                        System.out.println("Warning! Skipping corrupted line in save file: " + line);
                    }
                    line = reader.readLine();
                }
            } catch (IOException e) {
                throw new EsquieException("Oopsie! Something went wrong with importing previous tasks");
            }
        }
    }

    /**
     * Check if save file (./data/esquie.txt) exists. Otherwise, create.
     *
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
            throw new EsquieException("Something went wrong with the save file");
        }

    }

    /**
     * Parses a line of save file format, adds previous tasks to the taskList.
     *
     * @param input is a line from the save file (esquie.txt)
     * @param taskList is the task list to modify
     */
    private void parseString(String input, ArrayList<Task> taskList) throws EsquieException {
        if (input.trim().isEmpty()) {
            return;
        }

        String[] splitInput = input.split(" \\| ");
        try {
            switch (splitInput[0]) {
            case "T" -> {
                Task task = new Todo(splitInput[2], splitInput[1].equals("1"));
                taskList.add(task);
            }
            case "D" -> {
                Task task = new Deadline(splitInput[2], splitInput[3], splitInput[1].equals("1"));
                taskList.add(task);
            }
            case "E" -> {
                Task task = new Event(splitInput[2], splitInput[3], splitInput[4], splitInput[1].equals("1"));
                taskList.add(task);
            }
            default -> throw new EsquieException("Corrupted Save File");
            }
        } catch (IndexOutOfBoundsException e) {
            throw new EsquieException( "Corrupted Save File");
        } catch (DateTimeParseException e) {
            throw new EsquieException("Corrupted Date in Save File: " + input);
        }
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
            throw new EsquieException("Oopsie! Something went wrong with the saving");
        }
    }

    /**
     * Overwrites the entire save file whenever there is a mark/unmark/delete event.
     * @param taskList is the task list to modify.
     */
    public void overwriteAll(ArrayList<Task> taskList) throws EsquieException {
        try (BufferedWriter writer = Files.newBufferedWriter(filePath,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            for (Task task : taskList) {
                writer.write(task.saveString());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new EsquieException("Oopsie! Something went wrong with the saving");
        }
    }
}
