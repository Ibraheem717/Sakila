package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Language;

public interface languageRepository extends CrudRepository<Language, Long> {

}
