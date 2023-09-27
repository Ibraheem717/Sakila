package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Customer;

public interface customerRepository extends CrudRepository<Customer, Long> {

}