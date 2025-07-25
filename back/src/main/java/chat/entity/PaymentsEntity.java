package chat.entity;

import chat.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PaymentsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    private LocalDateTime dateTime;
    private float amount;
    private String currency;
    private String stripeId;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private UsersEntity user;

    @ManyToOne
    @JoinColumn(name = "agency_uuid")
    private AgenciesEntity agency;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
}
