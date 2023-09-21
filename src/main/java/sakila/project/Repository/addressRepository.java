package sakila.project.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import sakila.project.entities.Address;

public interface addressRepository extends CrudRepository<Address, Long> {

    @Query(value = "SELECT * FROM address WHERE UPPER(address) = ? AND UPPER(district) = ? AND city_id = ? AND phone = ?", nativeQuery = true)
    Address SearchAddress(String address, String district, Short city_id, String phone);

    @Query(value = "SELECT * FROM address WHERE UPPER(address) = ? AND UPPER(address2) = ? AND UPPER(district) = ? AND city_id = ? AND phone = ?", nativeQuery = true)
    Address SearchAddressWithAddress2(String address, String Address2, String district, Short city_id, String phone);

    @Query(value = "SELECT * FROM address WHERE UPPER(address) = ? AND UPPER(district) = ? AND city_id = ? AND UPPER(postal_code) = ? AND phone = ?", nativeQuery = true)
    Address SearchAddressWithPostCode(String address, String district, Short city_id, String postal_code, String phone);
    
    @Query(value = "SELECT * FROM address WHERE UPPER(address) = ? AND UPPER(address2) = ? AND UPPER(district) = ? AND city_id = ? AND UPPER(postal_code) = ? AND phone = ?", nativeQuery = true)
    Address SearchAddressAll(String address, String Address2,String district, Short city_id, String postal_code, String phone);
}