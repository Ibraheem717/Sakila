package sakila.project.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Blob;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="staff")
public class Staff {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Byte staff_id;
    private String first_name;
    private String last_name;
    private Short address_id;
    private Blob picture;
    private String email;
    private Byte store_id;
    private boolean active;
    private String username;
    private String password;
    private Timestamp last_update;
}
