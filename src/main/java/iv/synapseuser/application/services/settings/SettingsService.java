package iv.synapseuser.application.services.settings;

import iv.synapseuser.domain.constants.Theme;
import iv.synapseuser.domain.models.Settings;

import java.util.Optional;
import java.util.UUID;

public interface SettingsService {
    Settings create(Settings settings);
    Settings createDefault(UUID userId);
    Optional<Settings> findById(UUID id);
    Optional<Settings> findByUserId(UUID userId);
    Settings getOrCreate(UUID userId);
    Settings update(Settings settings);
    Settings resetToDefaults(UUID userId);
    void deleteByUserId(UUID userId);
    boolean existsByUserId(UUID userId);
    Settings updateTheme(UUID userId, Theme theme);
}
