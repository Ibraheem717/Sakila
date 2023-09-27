package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Category;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CategoryTest {

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category();
    }

    @Test
    void testCategoryId() {
        category.setCategory_id((byte) 1);
        assertEquals((byte) 1, category.getCategory_id());
    }

    @Test
    void testName() {
        category.setName("Action");
        assertEquals("Action", category.getName());
    }

    @Test
    void testLastUpdate() {
        category.setLast_update();
        assertNotNull(category.getLast_update());
    }
}
