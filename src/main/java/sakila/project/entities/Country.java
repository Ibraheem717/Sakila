package sakila.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="country")
public class Country {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short country_id;
    private String country;
    private Timestamp last_update;

    public void setLast_update() {
        this.last_update = Timestamp.valueOf(LocalDateTime.now());
    }
}
