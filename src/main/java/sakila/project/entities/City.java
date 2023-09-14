package sakila.project.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="city")
public class City {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short city_id;
    private String city;
    private Short country_id;
    private Timestamp last_update;
}    

