package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Address;

public interface addressRepository extends CrudRepository<Address, Long> {

    @Query(value = "SELECT * FROM address WHERE UPPER(address) = ? AND UPPER(district) = ? AND city_id = ? AND phone = ?", nativeQuery = true)
    Address SearchAddress(String address, String district, Short city_id, String phone);
    

}