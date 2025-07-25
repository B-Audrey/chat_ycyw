package chat.repository;

import chat.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UsersEntity, String> {

    UsersEntity findByEmail(String email);

    UsersEntity findByUuid(String uuid);
}