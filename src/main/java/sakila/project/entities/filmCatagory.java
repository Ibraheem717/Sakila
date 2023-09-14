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
@Table(name="film_catagory")
public class filmCatagory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short film_id;
    private Short catagory_id;
    private Timestamp last_update;
}
