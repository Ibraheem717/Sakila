package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Film;


public interface FilmRepository extends CrudRepository<Film, Long> {


}