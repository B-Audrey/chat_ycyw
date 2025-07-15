package chat.repository;

import chat.entity.SeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeedRepository extends JpaRepository<SeedEntity, Integer> {
}

