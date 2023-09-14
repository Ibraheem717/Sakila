package sakila.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="catagory")
public class Catagory {
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private Set<Film> films;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short category_id;
    private String name;
    private Timestamp last_update;
}
