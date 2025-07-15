package chat.model;

import chat.enums.Role;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class UserModel {
    private String uuid;
    private String email;
    private String firstName;
    private String lastName;
    private List<Role> role;
    private String createdAt;
    private String updatedAt;
    private String deletedAt;

    public UserModel(String uuid, String email, String firstName, String lastName , List<Role> roles, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.uuid = uuid;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = roles;
        this.createdAt = String.valueOf(createdAt);
        this.updatedAt = String.valueOf(updatedAt);
        this.deletedAt = String.valueOf(deletedAt);
    }

}