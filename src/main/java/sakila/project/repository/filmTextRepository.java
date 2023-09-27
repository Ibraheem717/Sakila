package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.filmText;

public interface filmTextRepository extends CrudRepository<filmText, Long> {

}
