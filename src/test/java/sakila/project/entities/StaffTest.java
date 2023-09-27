package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Staff;

import java.sql.Blob;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StaffTest {

    private Staff staff;

    @BeforeEach
    void setUp() {
        staff = new Staff();
    }

    @Test
    void testStaffId() {
        staff.setStaff_id((byte) 1);
        assertEquals((byte) 1, staff.getStaff_id());
    }

    @Test
    void testFirstName() {
        staff.setFirst_name("John");
        assertEquals("John", staff.getFirst_name());
    }

    @Test
    void testLastName() {
        staff.setLast_name("Doe");
        assertEquals("Doe", staff.getLast_name());
    }

    @Test
    void testAddressId() {
        staff.setAddress_id((short) 2);
        assertEquals((short) 2, staff.getAddress_id());
    }

    @Test
    void testPicture() {
        Blob picture = null; // You can set a Blob object here
        staff.setPicture(picture);
        assertEquals(picture, staff.getPicture());
    }

    @Test
    void testEmail() {
        staff.setEmail("john@example.com");
        assertEquals("john@example.com", staff.getEmail());
    }

    @Test
    void testStoreId() {
        staff.setStore_id((byte) 1);
        assertEquals((byte) 1, staff.getStore_id());
    }

    @Test
    void testActive() {
        staff.setActive(true);
        assertEquals(true, staff.isActive());
    }

    @Test
    void testUsername() {
        staff.setUsername("johndoe");
        assertEquals("johndoe", staff.getUsername());
    }

    @Test
    void testPassword() {
        staff.setPassword("password123");
        assertEquals("password123", staff.getPassword());
    }

    @Test
    void testLastUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        staff.setLast_update(Timestamp.valueOf(currentTime));
        assertNotNull(staff.getLast_update());
    }
}
