package chat.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@Entity
@Table(name = "vehicle_categories")
@Data
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class VehicleCategoriesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String category;

    @Column(nullable = false)
    private String carCode;
}
