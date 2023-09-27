package sakila.project.repository;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Language;

public interface LanguageRepository extends CrudRepository<Language, Long> {

}
