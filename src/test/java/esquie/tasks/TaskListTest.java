package esquie.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addTask_sizeIncrease() {
        TaskList taskList = new TaskList();
        Task task = new Task("Read book");
        taskList.add(task);
        assertEquals(1, taskList.size());
    }

    @Test
    public void deleteTask_sizeDecrease() {
        TaskList taskList = new TaskList();
        Task task = new Task("Read book");
        taskList.add(task);
        Task deletedTask = taskList.delete(0);
        assertEquals(0, taskList.size());
        assertEquals(task, deletedTask);
    }

    @Test
    public void getReturns_correctTask() {
        TaskList taskList = new TaskList();
        Task taskOne = new Task("Read book");
        Task taskTwo = new Task("Watch TV");
        taskList.add(taskOne);
        taskList.add(taskTwo);

        Task getOne = taskList.get(0);
        Task getTwo = taskList.get(1);
        assertEquals(taskOne, getOne);
        assertEquals(taskTwo, getTwo);
    }
}
