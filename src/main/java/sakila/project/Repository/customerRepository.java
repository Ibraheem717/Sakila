package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Customer;

public interface customerRepository extends CrudRepository<Customer, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customer ct WHERE ct.address_id = :address_id", nativeQuery = true)
    void DeleteByAddressID(Short address_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM customer ct WHERE ct.store_id = :store_id", nativeQuery = true)
    void DeleteByStoreID(Byte store_id);

    @Query(value = "SELECT customer_id FROM customer WHERE address_id = ?", nativeQuery = true)
    Iterable<Short> FindCustomerIDByAddressID(Short address_id);

    @Query(value = "SELECT customer_id FROM customer WHERE store_id = ?", nativeQuery = true)
    Iterable<Short> FindCustomerIDByStoreID(Byte store);
}