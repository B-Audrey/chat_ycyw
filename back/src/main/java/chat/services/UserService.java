package chat.services;

import chat.dto.UserSigninDto;
import chat.entity.UserEntity;
import chat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Get user by email
     *
     * @param email the email of the user
     * @return the user
     */
    public UserEntity getUserByEmail(String email) throws Exception {
        return Optional.ofNullable(userRepository.findByEmail(email))
                .orElseThrow(() -> new Exception("User not found"));
    }

    /**
     * Get user by uuid
     *
     * @param search the email or the name of the user
     * @return the user
     */
    public UserEntity findUserByMail(String search) {
        if (search.contains("@")) {
            return userRepository.findByEmail(search);
        } else {
            return userRepository.findByEmail(search);
        }
    }

}
