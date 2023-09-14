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
@Table(name="language")
public class Language {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Byte language_id;
    private String name;
    private Timestamp last_update;
}
