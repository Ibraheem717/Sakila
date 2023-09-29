package sakila.project.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.FilmActor;

public interface FilmActorRepository extends CrudRepository<FilmActor, Short> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM film_actor fa WHERE fa.actor_id = :actor_id", nativeQuery = true)
    void DeleteByActorID(Short actor_id);

    @Query(value = "SELECT film_id FROM film_actor WHERE actor_id = ?", nativeQuery = true)
    Iterable<Short> getByActorId(Short actor_id);
}
