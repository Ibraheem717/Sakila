package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Language;

public interface languageRepository extends CrudRepository<Language, Long> {

}
