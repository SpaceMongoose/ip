package esquie.commands;

import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.Task;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

/**
 * Representation of a DeleteCommand.
 */
public class DeleteCommand extends Command {
    private final int index;

    /**
     * Constructs a DeleteCommand object with a task index to delete.
     * @param index index for a specific task to delete.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Deletes a specified task from the tasklist.
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     * */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        try {
            Task removedTask = taskList.delete(index);
            storage.overwriteAll(taskList);
            ui.showMessage("Got it. I've removed this task:");
            ui.showMessage(removedTask.toString());
            ui.showMessage("Now you have " + taskList.size() + " tasks in the list.");

        } catch (IndexOutOfBoundsException e) {
            throw new EsquieException("Whoopsie! This task does not exist");
        }
    }

    /**
     * Returns false for this specific command.
     * @return returns true/false to indicate program exit.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
