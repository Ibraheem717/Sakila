package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Film;


public interface filmRepository extends CrudRepository<Film, Long> {


}