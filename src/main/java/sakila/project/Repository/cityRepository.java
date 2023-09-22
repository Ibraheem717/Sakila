package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.City;

public interface cityRepository extends CrudRepository<City, Short> {
    @Query(value = "SELECT * FROM city WHERE UPPER(city) = ?", nativeQuery = true)
    City SearchCityName(String city_name);
}
