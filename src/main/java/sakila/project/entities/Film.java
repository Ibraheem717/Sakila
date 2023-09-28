package sakila.project.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.time.Year;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="film")
public class Film {

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("categories")
    @JoinTable(name = "film_category",
            joinColumns = {
                    @JoinColumn(name = "film_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "category_id")
            }
    )
    @JsonManagedReference
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnoreProperties("actors")     
    @JoinTable(
        name = "film_actor",
        joinColumns = {  @JoinColumn(name = "film_id") } ,
        inverseJoinColumns ={ @JoinColumn(name = "actor_id")}
    )
    @JsonManagedReference
    private Set<Actor> actors;

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
    private String rating;
    private String special_features;
    private Timestamp last_update;
}
