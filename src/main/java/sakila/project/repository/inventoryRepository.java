package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Inventory;


public interface inventoryRepository extends CrudRepository<Inventory, Long> {

}