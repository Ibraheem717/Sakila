package sakila.project.entities;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;
import java.sql.Blob;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
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

    public void setLast_update() {
        this.last_update = Timestamp.valueOf(LocalDateTime.now());
    }
}