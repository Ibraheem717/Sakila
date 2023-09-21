package sakila.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="category")
public class Category {
    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @Transient
    @JsonBackReference
    private List<Film> films;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private byte category_id;
    private String name;
    private Timestamp last_update;
}
