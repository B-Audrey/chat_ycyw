package chat.utils.mappers;

import chat.entity.UsersEntity;
import chat.model.UserModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    public UserModel convertToUserModel(UsersEntity user) {
        return new UserModel(user.getUuid(),
                             user.getEmail(),
                             user.getFirstName(),
                             user.getLastName(),
                             user.getRoles(),
                             user.getCreatedAt(),
                             user.getUpdatedAt());
    }
}
