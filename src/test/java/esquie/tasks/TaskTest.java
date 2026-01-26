package esquie.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void taskGet_descriptionSuccess() {
        Task task = new Task("Read book");
        assertEquals("Read book", task.getDescription());
    }

    @Test
    public void taskGetStatus_iconSuccess() {
        Task taskDone = new Task("Read book", true);
        Task taskNotDone = new Task("Read book", false);
        assertEquals("X", taskDone.getStatusIcon());
        assertEquals(" ", taskNotDone.getStatusIcon());
    }

    @Test
    public void taskMark_success() {
        Task task = new Task("Read book");
        task.markIncomplete();
        assertEquals(" ", task.getStatusIcon());
        task.markComplete();
        assertEquals("X", task.getStatusIcon());
    }

    @Test
    public void taskToString_success() {
        Task task = new Task("Read book");
        assertEquals("[ ] Read book", task.toString());

        task.markComplete();
        assertEquals("[X] Read book", task.toString());
    }

    @Test
    public void taskSaveString_success() {
        Task task = new Task("Read book");
        assertEquals("0 | Read book", task.saveString());

        task.markComplete();
        assertEquals("1 | Read book", task.saveString());
    }
}
