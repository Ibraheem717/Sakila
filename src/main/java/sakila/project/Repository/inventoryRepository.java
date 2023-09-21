package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Inventory;


public interface inventoryRepository extends CrudRepository<Inventory, Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM inventory inv WHERE inv.store_id = :store_id", nativeQuery = true)
    void DeleteByStoreID(Byte store_id);

    @Query(value = "SELECT inventory_id FROM inventory WHERE store_id = ?", nativeQuery = true)
    Iterable<Integer> FindInventoryIDByStoreID(Byte store_id);
}