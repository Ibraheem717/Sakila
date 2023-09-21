package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Payment;

public interface paymentRepository extends CrudRepository<Payment, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payment pay WHERE pay.rental_id = :rental_id", nativeQuery = true)
    void DeleteByRentalID(Integer rental_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payment pay WHERE pay.customer_id = :customer_id", nativeQuery = true)
    void DeleteByCustomerID(Short customer_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM payment pay WHERE pay.staff_id = :staff_id", nativeQuery = true)
    void DeleteByStaffID(Byte staff_id);

    @Query(value = "SELECT payment_id FROM payment WHERE customer_id = ?", nativeQuery = true)
    Iterable<Short> FindPaymentIDByCustomerID(Short customer_id);

    @Query(value = "SELECT payment_id FROM payment WHERE rental_id = ?", nativeQuery = true)
    Iterable<Short> FindPaymentIDByRentalID(Integer rental_id);

    @Query(value = "SELECT payment_id FROM payment WHERE staff_id = ?", nativeQuery = true)
    Iterable<Short> FindPaymentIDByStaffID(Byte staff_id);
}
