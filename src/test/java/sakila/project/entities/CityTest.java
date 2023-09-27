package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.City;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CityTest {

    private City city;

    @BeforeEach
    void setUp() {
        city = new City();
    }

    @Test
    void testCityId() {
        city.setCity_id((short) 1);
        assertEquals((short) 1, city.getCity_id());
    }

    @Test
    void testCityName() {
        city.setCity("New York");
        assertEquals("New York", city.getCity());
    }

    @Test
    void testCountryId() {
        city.setCountry_id((short) 2);
        assertEquals((short) 2, city.getCountry_id());
    }

    @Test
    void testLastUpdate() {
        city.setLast_update();
        assertNotNull(city.getLast_update());
    }
}
