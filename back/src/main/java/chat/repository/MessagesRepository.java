package chat.repository;

import chat.entity.MessagesEntity;
import chat.entity.UsersEntity;
import chat.model.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<MessagesEntity, String> {
    MessagesEntity findByUuid(String uuid);

    Page<MessagesEntity> findMessagesEntitiesByTo(UsersEntity to, Pageable pageable);
}