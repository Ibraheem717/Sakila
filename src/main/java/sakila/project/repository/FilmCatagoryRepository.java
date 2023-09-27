package sakila.project.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import jakarta.transaction.Transactional;
import sakila.project.entities.FilmActor;

public interface FilmCatagoryRepository extends CrudRepository<FilmActor, Short> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM film_category fc WHERE fc.category_id = :category_id", nativeQuery = true)
    void DeleteByActorID(Byte category_id);
}
