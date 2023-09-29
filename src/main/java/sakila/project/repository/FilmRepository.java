package sakila.project.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Film;


public interface FilmRepository extends CrudRepository<Film, Long> {

    @Query(value = "SELECT * FROM film WHERE film_id = ?", nativeQuery = true)
    Film findByFilmId(Short filmId);


}