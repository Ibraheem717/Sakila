package sakila.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="actor")
public class Actor {
    @ManyToMany(mappedBy = "actors", fetch = FetchType.LAZY)
    private Set<Film> films;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short actor_id;
    private String first_name;
    private String last_name;
    private Timestamp last_update;

    public void setLast_update() {
        this.last_update = Timestamp.valueOf(LocalDateTime.now());
    }
}