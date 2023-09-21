package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Category;
import sakila.project.entities.Country;

public interface catagoryRepository extends CrudRepository<Category, Short> {
    @Query(value = "SELECT * FROM category WHERE UPPER(name) = ?", nativeQuery = true)
    Category SearchCategory(String category);
}