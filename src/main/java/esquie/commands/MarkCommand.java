package esquie.commands;

import esquie.exceptions.EsquieException;
import esquie.storage.Storage;
import esquie.tasks.Task;
import esquie.tasks.TaskList;
import esquie.ui.Ui;

/**
 * Representation of a MarkCommand.
 */
public class MarkCommand extends Command {
    private final int taskNumber;
    private final boolean isMark;

    /**
     * Constructs a MarkCommand with the task to mark / unmark.
     * @param taskNumber is the task index to mark/unmark.
     * @param isMark is to indicate if we should mark or unmark the task.
     */
    public MarkCommand(int taskNumber, boolean isMark) {
        this.taskNumber = taskNumber;
        this.isMark = isMark;
    }

    /**
     * Mark or Unmark the current task.
     * @param taskList is the tasklist to read through.
     * @param ui is the Ui object for user interaction.
     * @param storage is the Storage object for storage interaction.
     * */
    @Override
    public void execute(TaskList taskList, Ui ui, Storage storage) throws EsquieException {
        // Error Checking
        if (taskNumber < 0 || taskNumber >= taskList.size()) {
            throw new EsquieException("Whoopsie! This task does not exist");
        }

        Task currentTask = taskList.get(taskNumber);
        if (isMark) {
            currentTask.markComplete();
            ui.showMessage("WhooWhee! I've marked this task as done:");
        } else {
            currentTask.markIncomplete();
            ui.showMessage("WhooWhee! I've marked this task as not done yet:");
        }
        storage.overwriteAll(taskList);
        ui.showMessage(currentTask.toString());
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
