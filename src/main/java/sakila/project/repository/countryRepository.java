package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Country;

public interface countryRepository extends CrudRepository<Country, Long> {


}