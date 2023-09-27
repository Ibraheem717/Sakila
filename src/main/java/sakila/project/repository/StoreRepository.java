package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Store;


public interface StoreRepository extends CrudRepository<Store, Long> {

}

