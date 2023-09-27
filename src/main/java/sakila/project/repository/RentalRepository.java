package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Rental;


public interface RentalRepository extends CrudRepository<Rental, Long> {

}