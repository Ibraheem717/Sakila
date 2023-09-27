package sakila.project.entities;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Category;
import sakila.project.entities.Film;
import sakila.project.entities.Actor;

import java.sql.Timestamp;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FilmTest {

    private Film film;

    @BeforeEach
    void setUp() {
        film = new Film();
    }

    @Test
    void testFilmId() {
        film.setFilm_id((short) 1);
        assertEquals((short) 1, film.getFilm_id());
    }

    @Test
    void testDescription() {
        film.setDescription("Sample film description");
        assertEquals("Sample film description", film.getDescription());
    }

    @Test
    void testReleaseYear() {
        film.setRelease_year(Year.of(2022));
        assertEquals(Year.of(2022), film.getRelease_year());
    }

    @Test
    void testLanguageId() {
        film.setLanguage_id((byte) 1);
        assertEquals((byte) 1, film.getLanguage_id());
    }

    @Test
    void testOriginalLanguageId() {
        film.setOriginal_language_id((byte) 2);
        assertEquals((byte) 2, film.getOriginal_language_id());
    }

    @Test
    void testRentalDuration() {
        film.setRental_duration((byte) 3);
        assertEquals((byte) 3, film.getRental_duration());
    }

    @Test
    void testRentalRate() {
        film.setRental_rate(4);
        assertEquals(4, film.getRental_rate());
    }

    @Test
    void testLength() {
        film.setLength((short) 120);
        assertEquals((short) 120, film.getLength());
    }

    @Test
    void testReplacementCost() {
        film.setReplacement_cost((short) 15);
        assertEquals((short) 15, film.getReplacement_cost());
    }

    @Test
    void testRating() {
        film.setRating("PG-13");
        assertEquals("PG-13", film.getRating());
    }

    @Test
    void testSpecialFeatures() {
        film.setSpecial_features("Trailers, Commentaries");
        assertEquals("Trailers, Commentaries", film.getSpecial_features());
    }

    @Test
    void testLastUpdate() {
        Timestamp lastUpdate = Timestamp.valueOf("2023-09-23 10:30:00");
        film.setLast_update(lastUpdate);
        assertEquals(lastUpdate, film.getLast_update());
    }

    @Test
    void testCategories() {
        Set<Category> categories = new HashSet<>();
        Category category1 = new Category();
        category1.setCategory_id((byte) 1);
        category1.setName("Category 1");

        Category category2 = new Category();
        category2.setCategory_id((byte) 2);
        category2.setName("Category 2");

        categories.add(category1);
        categories.add(category2);

        film.setCategories(categories);
        assertEquals(2, film.getCategories().size());
    }

    @Test
    void testActors() {
        Set<Actor> actors = new HashSet<>();
        Actor actor1 = new Actor();
        actor1.setActor_id((short) 1);
        actor1.setFirst_name("John");
        actor1.setLast_name("Doe");

        Actor actor2 = new Actor();
        actor2.setActor_id((short) 2);
        actor2.setFirst_name("Jane");
        actor2.setLast_name("Smith");

        actors.add(actor1);
        actors.add(actor2);

        film.setActors(actors);
        assertEquals(2, film.getActors().size());
    }
}


