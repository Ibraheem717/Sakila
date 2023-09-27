package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Rental;


public interface rentalRepository extends CrudRepository<Rental, Long> {

}