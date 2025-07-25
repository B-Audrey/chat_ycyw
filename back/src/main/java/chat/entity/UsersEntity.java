package chat.entity;

import chat.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@RequiredArgsConstructor
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class) // Pour les champs created_at et updated_at
public class UsersEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    private String lastName;
    private String firstName;
    private String adresse;
    private String city;
    private String postalCode;
    private String country;
    private String complementaryAdress;
    private String adressNumber;

    private LocalDate birthDate;
    private String striped;
    private LocalDateTime lastConnectionAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private List<Role> roles;

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

    @OneToMany(mappedBy = "user")
    private List<RentalsEntity> rentals;

    @OneToMany(mappedBy = "user")
    private List<PaymentsEntity> payments;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email; // use Email as name to find user
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
