package sakila.project.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="rental")
public class Rental {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer rental_id;
    private Date rental_date;
    private Integer inventory_id;
    private Short customer_id;
    private Date return_date;
    private Timestamp last_update;
}
