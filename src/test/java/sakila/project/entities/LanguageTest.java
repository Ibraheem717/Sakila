package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Language;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LanguageTest {

    private Language language;

    @BeforeEach
    void setUp() {
        language = new Language();
    }

    @Test
    void testLanguageId() {
        language.setLanguage_id((byte) 1);
        assertEquals((byte) 1, language.getLanguage_id());
    }

    @Test
    void testName() {
        language.setName("English");
        assertEquals("English", language.getName());
    }

    @Test
    void testLastUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        language.setLast_update(Timestamp.valueOf(currentTime));
        assertNotNull(language.getLast_update());
    }
}
