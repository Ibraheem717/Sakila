package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Customer;

public interface customerRepository extends CrudRepository<Customer, Long> {

}