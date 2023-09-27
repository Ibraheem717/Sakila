package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Staff;


public interface StaffRepository extends CrudRepository<Staff, Long> {

}