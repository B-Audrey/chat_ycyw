package chat.entity;

import chat.enums.RentalStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "rentals")
@Data
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class RentalsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "user_uuid", nullable = false)
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "vehicule_uuid", nullable = false)
    private VehiclesEntity vehicle;

    @ManyToOne
    @JoinColumn(name = "departure_agency_uuid", nullable = false)
    private AgenciesEntity departureAgency;

    @ManyToOne
    @JoinColumn(name = "arrival_agency_uuid", nullable = false)
    private AgenciesEntity arrivalAgency;

    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    @ManyToOne
    @JoinColumn(name = "vehicule_category_uuid")
    private VehicleCategoriesEntity vehiculeCategory;

    @ManyToOne
    @JoinColumn(name = "chosen_vehicule_uuid")
    private VehiclesEntity chosenVehiculeUuid;

    private float price;

    @ManyToOne
    @JoinColumn(name = "payment_uuid")
    private PaymentsEntity payment;

    @Enumerated(EnumType.STRING)
    private RentalStatus status;
}