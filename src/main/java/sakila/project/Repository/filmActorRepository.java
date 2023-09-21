package sakila.project.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.filmActor;

public interface filmActorRepository extends CrudRepository<filmActor, Short> {

    @Query(value = "SELECT film_id FROM film_actor WHERE actor_id = ? ", nativeQuery = true)
    Iterable<filmActor> findByActorID(Short actor_id);

    @Query(value = "SELECT * FROM film_actor WHERE film_id = ? ", nativeQuery = true)
    Iterable<filmActor> findByFilmID(Short film_id);


}
