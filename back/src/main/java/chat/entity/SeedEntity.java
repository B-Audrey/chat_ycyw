package chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "seeds")
@Data
public class SeedEntity {

    @Id
    private int id; // use voluntary ids here for manual generation tha would be impossible with uuids

    @Column(nullable = false)
    private String name;
}
