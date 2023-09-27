package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Staff;


public interface staffRepository extends CrudRepository<Staff, Long> {

}