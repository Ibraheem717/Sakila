package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Rental;


public interface rentalRepository extends CrudRepository<Rental, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rental ren WHERE ren.inventory_id = :inventory_id", nativeQuery = true)
    void DeleteByInventoryID(Integer inventory_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rental ren WHERE ren.customer_id = :customer_id", nativeQuery = true)
    void DeleteByCustomerID(Short customer_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM rental ren WHERE ren.staff_id = :staff_id", nativeQuery = true)
    void DeleteByStaffID(Byte staff_id);

    @Query(value = "SELECT rental_id FROM rental WHERE customer_id = ?", nativeQuery = true)
    Iterable<Short> FindRentalIDByCustomerID(Short customer_id);

    @Query(value = "SELECT rental_id FROM rental WHERE staff_id = ?", nativeQuery = true)
    Iterable<Short> FindRentalIDByStaffID(Byte staff_id);

    @Query(value = "SELECT rental_id FROM rental WHERE inventory_id = ?", nativeQuery = true)
    Iterable<Short> FindRentalIDByInventoryID(Short inventory_id);
}