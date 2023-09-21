package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Store;


public interface storeRepository extends CrudRepository<Store, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM store s WHERE s.address_id = :address_id", nativeQuery = true)
    void DeleteByAddressID(Short address_id);
    
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM store s WHERE s.manager_staff_id = :manager_staff_id", nativeQuery = true)
    void DeleteByStaffID(Byte manager_staff_id);

    @Query(value = "SELECT store_id FROM store WHERE address_id = ?", nativeQuery = true)
    Iterable<Byte> FindStoreIDByAddressID(Short address_id);

    @Query(value = "SELECT store_id FROM store WHERE manager_staff_id = ?", nativeQuery = true)
    Iterable<Byte> FindStoreIDByStaffID(Byte manager_staff_id);
}

