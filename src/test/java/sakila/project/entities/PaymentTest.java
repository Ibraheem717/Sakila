package sakila.project.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sakila.project.entities.Payment;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaymentTest {

    private Payment payment;

    @BeforeEach
    void setUp() {
        payment = new Payment();
    }

    @Test
    void testPaymentId() {
        payment.setPayment_id((short) 1);
        assertEquals((short) 1, payment.getPayment_id());
    }

    @Test
    void testCustomerId() {
        payment.setCustomer_id((short) 2);
        assertEquals((short) 2, payment.getCustomer_id());
    }

    @Test
    void testStaffId() {
        payment.setStaff_id((byte) 3);
        assertEquals((byte) 3, payment.getStaff_id());
    }

    @Test
    void testRentalId() {
        payment.setRental_id(4);
        assertEquals(4, payment.getRental_id());
    }

    @Test
    void testAmount() {
        payment.setAmount(100);
        assertEquals(100, payment.getAmount());
    }

    @Test
    void testPaymentDate() {
        Date date = Date.valueOf("2023-09-24");
        payment.setPayment_date(date);
        assertEquals(date, payment.getPayment_date());
    }

    @Test
    void testLastUpdate() {
        LocalDateTime currentTime = LocalDateTime.now();
        payment.setLast_update(Timestamp.valueOf(currentTime));
        assertNotNull(payment.getLast_update());
    }
}
