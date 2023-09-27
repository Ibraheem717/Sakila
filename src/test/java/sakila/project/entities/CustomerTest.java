package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Customer;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CustomerTest {

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
    }

    @Test
    void testCustomerId() {
        customer.setCustomer_id((short) 1);
        assertEquals((short) 1, customer.getCustomer_id());
    }

    @Test
    void testCityId() {
        customer.setCity_id((byte) 2);
        assertEquals((byte) 2, customer.getCity_id());
    }

    @Test
    void testFirstName() {
        customer.setFirst_name("John");
        assertEquals("John", customer.getFirst_name());
    }

    @Test
    void testLastName() {
        customer.setLast_name("Doe");
        assertEquals("Doe", customer.getLast_name());
    }

    @Test
    void testEmail() {
        customer.setEmail("john.doe@example.com");
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    @Test
    void testAddressId() {
        customer.setAddress_id((short) 3);
        assertEquals((short) 3, customer.getAddress_id());
    }

    @Test
    void testActive() {
        customer.setActive(true);
        assertTrue(customer.isActive());
    }

    @Test
    void testCreateDate() {
        Date createDate = Date.valueOf("2023-09-23");
        customer.setCreate_date(createDate);
        assertEquals(createDate, customer.getCreate_date());
    }

    @Test
    void testLastUpdate() {
        Timestamp lastUpdate = Timestamp.valueOf("2023-09-23 10:30:00");
        customer.setLast_update(lastUpdate);
        assertEquals(lastUpdate, customer.getLast_update());
    }
}

