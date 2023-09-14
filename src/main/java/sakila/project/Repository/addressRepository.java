package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Address;

public interface addressRepository extends CrudRepository<Address, Long> {

}