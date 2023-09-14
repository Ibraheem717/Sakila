package sakila.project.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.sql.Timestamp;

@Entity
@Table(name="address")
public class Address {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short address_id;
    private String address;
    private String address2;
    private String district;
    private Short city_id;
    private String postal_code;
    private String phone;
    private Timestamp last_update;

    public Short getId() {
        return address_id;
    }
    public String getAddress() {
        return address;
    }    
    public String getAddress2() {
        return address2;
    }    
    public String getDistrict() {
        return district;
    }
    public Short getCityId() {
        return city_id;
    }
    public String getPostalCode() {
        return postal_code;
    }
    public String getPhone() {
        return phone;
    }
    public Timestamp getLastUpdate() {
        return last_update;
    }  
    public void setLast_update() {
        this.last_update = Timestamp.valueOf(LocalDateTime.now());
    }
}