package esquie.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import esquie.exceptions.EsquieException;

public class EventTest {

    @Test
    public void event_add_success() throws EsquieException {
        Event event = new Event("Read book", "2026-01-01", "2026-01-02");
        assertEquals("[E][ ] Read book (from: 1 Jan 2026 to: 2 Jan 2026)", event.toString());

        Event eventWithTime = new Event("Read book", "2026-01-01 1000", "2026-01-02 2359");
        assertEquals("[E][ ] Read book (from: 1 Jan 2026, 1000H to: 2 Jan 2026, 2359H)",
                    eventWithTime.toString());
    }

    @Test
    public void event_add_saveString() throws EsquieException {
        Event event = new Event("Read book", "2026-01-01", "2026-01-02");
        assertEquals("E | 0 | Read book | 2026-01-01 0000 | 2026-01-02 0000", event.saveString());

        Event eventWithTime = new Event("Read book", "2026-01-01 1000", "2026-01-02 2359");
        assertEquals("E | 0 | Read book | 2026-01-01 1000 | 2026-01-02 2359", eventWithTime.saveString());
    }

    @Test
    public void event_manual_mark() throws EsquieException {
        Event eventMarked = new Event("Read book", "2026-01-01", "2026-01-02", true);
        Event eventUnmarked = new Event("Read book", "2026-01-01 1000", "2026-01-02 0800",
                false);

        assertEquals("[E][X] Read book (from: 1 Jan 2026 to: 2 Jan 2026)", eventMarked.toString());
        assertEquals("E | 1 | Read book | 2026-01-01 0000 | 2026-01-02 0000", eventMarked.saveString());
        assertEquals("[E][ ] Read book (from: 1 Jan 2026, 1000H to: 2 Jan 2026, 0800H)",
                eventUnmarked.toString());
        assertEquals("E | 0 | Read book | 2026-01-01 1000 | 2026-01-02 0800", eventUnmarked.saveString());
    }

    @Test
    public void event_initialization_failure() {
        EsquieException exception = assertThrows(EsquieException.class, () -> {
                new Event("Read book", "2026-01-02", "2026-01-01");
            }
        );

        assertEquals("Whoopsie! End time cannot be before Start time!", exception.getMessage());

        EsquieException exceptionWithTime = assertThrows(EsquieException.class, () -> {
            new Event("Read book", "2026-01-01 2359", "2026-01-01 0000");
        });

        assertEquals("Whoopsie! End time cannot be before Start time!", exceptionWithTime.getMessage());
    }
}
