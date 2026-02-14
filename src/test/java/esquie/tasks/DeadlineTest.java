package esquie.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import esquie.exceptions.EsquieException;

public class DeadlineTest {

    @Test
    public void deadline_add_success() throws EsquieException {
        Deadline deadline = new Deadline("Read book", "2026-01-01");
        assertEquals("[D][ ] Read book (by: 1 Jan 2026)", deadline.toString());

        Deadline deadlineWithTime = new Deadline("Read book", "2026-01-01 1300");
        assertEquals("[D][ ] Read book (by: 1 Jan 2026, 1300H)", deadlineWithTime.toString());
    }

    @Test
    public void deadline_add_saveString() throws EsquieException {
        Deadline deadline = new Deadline("Read book", "2026-01-01");
        assertEquals("D | 0 | Read book | 2026-01-01 0000", deadline.saveString());

        Deadline deadlineWithTime = new Deadline("Read book", "2026-01-01 1300");
        assertEquals("D | 0 | Read book | 2026-01-01 1300", deadlineWithTime.saveString());
    }

    @Test
    public void deadline_manual_mark() {
        Deadline deadlineMarked = new Deadline("Read book", "2026-01-01", true);
        Deadline deadlineUnmarked = new Deadline("Read book", "2026-01-01", false);

        //  the print and save string for a marked task
        assertEquals("[D][X] Read book (by: 1 Jan 2026)", deadlineMarked.toString());
        assertEquals("D | 1 | Read book | 2026-01-01 0000", deadlineMarked.saveString());

        //  the print and save string for a unmarked task
        assertEquals("[D][ ] Read book (by: 1 Jan 2026)", deadlineUnmarked.toString());
        assertEquals("D | 0 | Read book | 2026-01-01 0000", deadlineUnmarked.saveString());
    }
}
