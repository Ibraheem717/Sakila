package sakila.project.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.Store;


public interface storeRepository extends CrudRepository<Store, Long> {

}

