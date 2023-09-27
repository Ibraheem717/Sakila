package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Country;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CountryTest {

    private Country country;

    @BeforeEach
    void setUp() {
        country = new Country();
    }

    @Test
    void testCountryId() {
        country.setCountry_id((short) 1);
        assertEquals((short) 1, country.getCountry_id());
    }

    @Test
    void testCountryName() {
        country.setCountry("United States");
        assertEquals("United States", country.getCountry());
    }

    @Test
    void testLastUpdate() {
        country.setLast_update();
        assertNotNull(country.getLast_update());
    }
}
