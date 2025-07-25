package chat.entity;

import chat.enums.VehiculeStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "vehicles")
@Data
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class VehiclesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String imageUrl;

    @Column(nullable = false)
    private String label;

    private String brand;
    private String modelRef;
    private String numberPlate;
    private String color;
    private String description;
    private int seats;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehiculeStatus status;

    @ManyToOne
    @JoinColumn(name = "agency_owner_uuid")
    private AgenciesEntity agencyOwner;

    @ManyToOne
    @JoinColumn(name = "vehicule_category_uuid")
    private VehicleCategoriesEntity vehiculeCategory;
}

