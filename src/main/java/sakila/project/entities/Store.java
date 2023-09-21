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
@Table(name="store")
public class Store {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Byte store_id;
    private Byte manager_store_id;
    private Short address_id;
    private Timestamp last_update;
}
