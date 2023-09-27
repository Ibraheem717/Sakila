package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Address;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AddressTest {

    private Address address;

    @BeforeEach
    void setUp() {
        address = new Address();
    }

    @Test
    void testAddressId() {
        address.setAddress_id((short) 1);
        assertEquals((short) 1, address.getAddress_id());
    }

    @Test
    void testAddress() {
        address.setAddress("123 Main St");
        assertEquals("123 Main St", address.getAddress());
    }

    @Test
    void testAddress2() {
        address.setAddress2("Apt 456");
        assertEquals("Apt 456", address.getAddress2());
    }

    @Test
    void testDistrict() {
        address.setDistrict("Downtown");
        assertEquals("Downtown", address.getDistrict());
    }

    @Test
    void testCityId() {
        address.setCity_id((short) 2);
        assertEquals((short) 2, address.getCity_id());
    }

    @Test
    void testPostalCode() {
        address.setPostal_code("12345");
        assertEquals("12345", address.getPostal_code());
    }

    @Test
    void testPhone() {
        address.setPhone("555-1234");
        assertEquals("555-1234", address.getPhone());
    }

    @Test
    void testLastUpdate() {
        address.setLast_update();
        assertNotNull(address.getLast_update());
    }
}
