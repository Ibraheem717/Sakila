package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.FilmText;

public interface filmTextRepository extends CrudRepository<FilmText, Long> {

}
