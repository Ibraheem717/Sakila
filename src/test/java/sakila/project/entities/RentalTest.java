package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Rental;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RentalTest {

    private Rental rental;

    @BeforeEach
    void setUp() {
        rental = new Rental();
    }

    @Test
    void testRentalId() {
        rental.setRental_id(1);
        assertEquals(1, rental.getRental_id());
    }

    @Test
    void testRentalDate() {
        Date date = Date.valueOf("2023-09-24");
        rental.setRental_date(date);
        assertEquals(date, rental.getRental_date());
    }

    @Test
    void testInventoryId() {
        rental.setInventory_id(2);
        assertEquals(2, rental.getInventory_id());
    }

    @Test
    void testCustomerId() {
        rental.setCustomer_id((short) 3);
        assertEquals((short) 3, rental.getCustomer_id());
    }

    @Test
    void testReturnDate() {
        Date date = Date.valueOf("2023-09-25");
        rental.setReturn_date(date);
        assertEquals(date, rental.getReturn_date());
    }

    @Test
    void testLastUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        rental.setLast_update(Timestamp.valueOf(currentTime));
        assertNotNull(rental.getLast_update());
    }
}
