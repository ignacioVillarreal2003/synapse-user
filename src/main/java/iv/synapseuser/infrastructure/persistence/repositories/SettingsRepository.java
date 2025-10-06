package iv.synapseuser.infrastructure.persistence.repositories;


import iv.synapseuser.domain.models.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SettingsRepository extends JpaRepository<Settings, UUID> {
    Optional<Settings> findByUser_Id(UUID userId);

    boolean existsByUser_Id(UUID userId);
}
