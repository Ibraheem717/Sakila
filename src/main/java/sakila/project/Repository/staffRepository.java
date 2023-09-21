package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Staff;


public interface staffRepository extends CrudRepository<Staff, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM staff sf WHERE sf.address_id = :address_id", nativeQuery = true)
    void DeleteByAddressID(Short address_id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM staff sf WHERE sf.store_id = :store_id", nativeQuery = true)
    void DeleteByStoreID(Byte store_id);

    @Query(value = "SELECT staff_id FROM staff WHERE address_id = ?", nativeQuery = true)
    Iterable<Byte> FindStaffIDByAddressID(Short address_id);

    @Query(value = "SELECT staff_id FROM staff WHERE store_id = ?", nativeQuery = true)
    Iterable<Byte> FindStaffIDByStoreID(Byte store_id);
}