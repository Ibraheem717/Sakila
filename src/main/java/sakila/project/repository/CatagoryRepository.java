package sakila.project.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Category;

public interface CatagoryRepository extends CrudRepository<Category, Short> {
    @Query(value = "SELECT * FROM category WHERE UPPER(name) = ?", nativeQuery = true)
    Category SearchCategory(String category);
}