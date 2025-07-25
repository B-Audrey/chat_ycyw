package chat.entity;

import chat.enums.MessageStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "messages")
@EntityListeners(AuditingEntityListener.class)
public class MessagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @ManyToOne
    @JoinColumn(name = "from_uuid")
    private UsersEntity from;

    @ManyToOne
    @JoinColumn(name = "to_uuid")
    private UsersEntity to;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Enumerated(EnumType.STRING)
    private MessageStatus status;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column()
    private LocalDateTime updatedAt;
    @Column()
    private LocalDateTime deletedAt;
    @PreRemove
    protected void onDelete() {
        deletedAt = LocalDateTime.now();
    }

}
