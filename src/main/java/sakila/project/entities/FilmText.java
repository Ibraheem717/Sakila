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
@Table(name="film_text")
public class FilmText {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short film_id;
    private String title;
    private Timestamp last_update;
}
