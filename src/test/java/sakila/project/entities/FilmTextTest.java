package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.FilmText;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class FilmTextTest {

    private FilmText filmText;

    @BeforeEach
    void setUp() {
        filmText = new FilmText();
    }

    @Test
    void testFilmId() {
        filmText.setFilm_id((short) 1);
        assertEquals((short) 1, filmText.getFilm_id());
    }

    @Test
    void testTitle() {
        filmText.setTitle("Sample Film Title");
        assertEquals("Sample Film Title", filmText.getTitle());
    }

    @Test
    void testLastUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        filmText.setLast_update(Timestamp.valueOf(currentTime));
        assertNotNull(filmText.getLast_update());
    }
}
