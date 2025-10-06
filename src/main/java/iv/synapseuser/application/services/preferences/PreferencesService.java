package iv.synapseuser.application.services.preferences;

import iv.synapseuser.domain.models.Preferences;

import java.util.Optional;
import java.util.UUID;

public interface PreferencesService {
    Preferences create(Preferences preferences);
    Preferences createDefault(UUID userId);
    Optional<Preferences> findById(UUID id);
    Optional<Preferences> findByUserId(UUID userId);
    Preferences getOrCreate(UUID userId);
    Preferences update(Preferences preferences);
    Preferences resetToDefaults(UUID userId);
    void deleteByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
}
