package iv.synapseuser.application.services.settings;

import iv.synapseuser.domain.constants.Theme;
import iv.synapseuser.domain.models.Settings;
import iv.synapseuser.domain.models.User;
import iv.synapseuser.infrastructure.persistence.repositories.SettingsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SettingsServiceImpl implements SettingsService {

    private final SettingsRepository settingsRepository;

    @Override
    @Transactional
    public Settings create(Settings settings) {
        log.debug("Creating settings: {}", settings);
        validateSettings(settings);
        return settingsRepository.save(settings);
    }

    @Override
    @Transactional
    public Settings createDefault(UUID userId) {
        log.debug("Creating default settings for user: {}", userId);
        
        if (existsByUserId(userId)) {
            log.warn("Settings already exist for user: {}", userId);
            return getOrCreate(userId);
        }

        Settings defaultSettings = Settings.builder()
                .theme(Theme.LIGHT)
                .notificationsEnabled(true)
                .emailNotifications(true)
                .weeklyReport(false)
                .build();
        
        User user = User.builder().id(userId).build();
        defaultSettings.setUser(user);
        
        return settingsRepository.save(defaultSettings);
    }

    @Override
    public Optional<Settings> findById(UUID id) {
        log.debug("Finding settings by id: {}", id);
        return settingsRepository.findById(id);
    }

    @Override
    public Optional<Settings> findByUserId(UUID userId) {
        log.debug("Finding settings by user id: {}", userId);
        return settingsRepository.findByUser_Id(userId);
    }

    @Override
    @Transactional
    public Settings getOrCreate(UUID userId) {
        log.debug("Getting or creating settings for user: {}", userId);
        return findByUserId(userId)
                .orElseGet(() -> createDefault(userId));
    }

    @Override
    @Transactional
    public Settings update(Settings settings) {
        log.debug("Updating settings: {}", settings);
        validateSettings(settings);
        return settingsRepository.save(settings);
    }

    @Override
    @Transactional
    public Settings resetToDefaults(UUID userId) {
        log.debug("Resetting settings to defaults for user: {}", userId);
        
        Settings settings = findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Settings not found for user: " + userId));
        
        settings.setTheme(Theme.LIGHT);
        settings.setNotificationsEnabled(true);
        settings.setEmailNotifications(true);
        settings.setWeeklyReport(false);
        
        return settingsRepository.save(settings);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        log.debug("Deleting settings for user: {}", userId);
        settingsRepository.findByUser_Id(userId)
                .ifPresent(settingsRepository::delete);
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return settingsRepository.existsByUser_Id(userId);
    }

    @Override
    @Transactional
    public Settings updateTheme(UUID userId, Theme theme) {
        log.debug("Updating theme for user: {} to: {}", userId, theme);
        validateUserId(userId);
        validateTheme(theme);
        
        Settings settings = getSettingsOrThrow(userId);
        settings.setTheme(theme);
        
        return settingsRepository.save(settings);
    }

    private Settings getSettingsOrThrow(UUID userId) {
        return findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Settings not found for user: " + userId));
    }

    private void validateSettings(Settings settings) {
        if (settings == null) {
            throw new IllegalArgumentException("Settings cannot be null");
        }
        if (settings.getUser() == null || settings.getUser().getId() == null) {
            throw new IllegalArgumentException("Settings must have a valid user");
        }
        if (settings.getTheme() == null) {
            throw new IllegalArgumentException("Theme cannot be null");
        }
        if (settings.getNotificationsEnabled() == null) {
            throw new IllegalArgumentException("Notifications enabled cannot be null");
        }
        if (settings.getEmailNotifications() == null) {
            throw new IllegalArgumentException("Email notifications cannot be null");
        }
        if (settings.getWeeklyReport() == null) {
            throw new IllegalArgumentException("Weekly report cannot be null");
        }
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private void validateTheme(Theme theme) {
        if (theme == null) {
            throw new IllegalArgumentException("Theme cannot be null");
        }
    }
}
