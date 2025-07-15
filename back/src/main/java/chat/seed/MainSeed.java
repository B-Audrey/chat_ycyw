package chat.seed;

import chat.entity.SeedEntity;
import chat.entity.UserEntity;
import chat.enums.Role;
import chat.repository.SeedRepository;
import chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MainSeed implements CommandLineRunner {

    @Autowired
    private SeedRepository seedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String initializationMode;

    private final String firstUserMail = "user@dev.fr";
    private final String secondUserMail = "collab@dev.fr";


    /**
     * This method is called when the application is started.
     * It is used to create fake data in the database.
     * It is called only if the application is in "always" or "embedded" initialization mode.
     * The run method is overridden to make chat.seed as we want.
     *
     * @param args the command line arguments passed to the application
     */
    @Override
    public void run(String... args) {

        if (initializationMode.equals("always") || initializationMode.equals("embedded")) {
            this.createUser();
            // ...add more seeds here
            // seeds are created with id to be created only once by a determined order
            // think about increment ids correctly !!
        }
    }


    private void createUser() {
        int userSeedId = 1;
        if (!seedRepository.existsById(userSeedId)) {
            UserEntity firstUser = new UserEntity();
            firstUser.setEmail(firstUserMail);
            firstUser.setFirstName("User");
            firstUser.setLastName("Client");
            UserEntity secondUser = new UserEntity();
            secondUser.setEmail(secondUserMail);
            secondUser.setFirstName("User");
            secondUser.setLastName("Collaborator");
            String password = this.passwordEncoder.encode("password");
            firstUser.setPassword(password);
            secondUser.setPassword(password);
            List<Role> userRoles = new ArrayList<>();
            userRoles.add(Role.ROLE_USER);
            firstUser.setRoles(userRoles);
            List<Role> collabRoles = new ArrayList<>();
            collabRoles.add(Role.ROLE_COLLABORATOR);
            secondUser.setRoles(collabRoles);
            this.userRepository.save(firstUser);
            this.userRepository.save(secondUser);
            SeedEntity seed = new SeedEntity();
            seed.setId(userSeedId);
            seed.setName("add fake user");
            seedRepository.save(seed);
        }
    }

}
