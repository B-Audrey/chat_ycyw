package chat.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "agencies")
@Data
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AgenciesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private String agencyName;

    private String adresse;
    private String city;
    private String postalCode;
    private String country;
    private String complementaryAdress;
    private String adressNumber;

    private String phoneNumber;
    private String email;

    @OneToMany(mappedBy = "departureAgency")
    private List<RentalsEntity> departures;

    @OneToMany(mappedBy = "arrivalAgency")
    private List<RentalsEntity> arrivals;

    @OneToMany(mappedBy = "agency")
    private List<PaymentsEntity> payments;

    @OneToMany(mappedBy = "agencyOwner")
    private List<VehiclesEntity> vehicles;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
