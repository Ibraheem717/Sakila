package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import sakila.project.entities.Address;
import sakila.project.entities.Country;

public interface countryRepository extends CrudRepository<Country, Long> {

    @Query(value = "SELECT * FROM country WHERE UPPER(country) = ?", nativeQuery = true)
    Country SearchCountry(String country);

}