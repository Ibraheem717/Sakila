package sakila.project.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.time.Year;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

enum Age {
    G,
    PG,
    PG_13,
    R,
    NC_17;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="film")
public class Film {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("films")
    @JoinTable(name = "film_category",
        joinColumns = {
                @JoinColumn(name = "film_id")
        },
        inverseJoinColumns = {
                @JoinColumn(name = "category_id")
        }
    )
    private Set<Catagory> categories;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
        name = "film_actor",
        joinColumns = @JoinColumn(name = "actor_id"),
        inverseJoinColumns = @JoinColumn(name = "film_id")
    )
    private Set<Film> films = new HashSet<>();

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Short film_id;
    private String description;
    private Year release_year;
    private Byte language_id;
    private Byte original_language_id;
    private Byte rental_duration;
    private Integer rental_rate;
    private Short length;
    private Short replacement_cost;
    @Column(columnDefinition = "ENUM('G','PG','PG-13','R','NC-17')",
            name="rating")
    @Enumerated(EnumType.STRING)
    private Age age;
    private Set<String> special_features;
    private Timestamp last_update;
}
