package esquie.commands;

import esquie.common.Messages;
import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.Task;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

/**
 * Representation of a AddCommand.
 */
public class AddCommand extends Command {
    private Task task;

    /**
     * Constructs a AddCommand object with a task to add.
     * @param task Task to add to the taskList (e.g. Todo, Deadline, Event).
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    /**
     * Adds the current tasks in the tasklist.
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        if (taskList.size() >= TaskList.MAX_TASKLIST_SIZE) {
            throw new EsquieException(Messages.ERR_TASKLIST_FULL);
        }
        taskList.add(task);
        storage.writeTask(task);
        ui.showTaskAdded(task, taskList.size());
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
