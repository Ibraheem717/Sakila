package sakila.project.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Actor;

public interface actorRepository extends CrudRepository<Actor, Long> {

    @Query(value = "SELECT * FROM actor WHERE UPPER(first_name) = ? AND UPPER(last_name) = ?", nativeQuery = true)
    Actor findByFirstName(String first_name, String last_name);


}
