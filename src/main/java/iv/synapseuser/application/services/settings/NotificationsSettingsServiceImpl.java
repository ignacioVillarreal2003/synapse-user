package iv.synapseuser.application.services.settings;

import iv.synapseuser.domain.models.Settings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationsSettingsServiceImpl implements NotificationsSettingsService {

    private final SettingsService settingsService;

    @Override
    @Transactional
    public Settings updateNotificationsSettings(UUID userId,
                                                Boolean notificationsEnabled,
                                                Boolean emailNotifications,
                                                Boolean weeklyReport) {
        log.debug("Updating all notification settings for user: {}", userId);
        validateUserId(userId);
        
        Settings settings = getSettingsOrThrow(userId);
        
        if (notificationsEnabled != null) {
            validateBooleanValue(notificationsEnabled, "notifications enabled");
            settings.setNotificationsEnabled(notificationsEnabled);
        }
        
        if (emailNotifications != null) {
            validateBooleanValue(emailNotifications, "email notifications");
            settings.setEmailNotifications(emailNotifications);
        }
        
        if (weeklyReport != null) {
            validateBooleanValue(weeklyReport, "weekly report");
            settings.setWeeklyReport(weeklyReport);
        }
        
        return settingsService.update(settings);
    }

    @Override
    @Transactional
    public Settings updateNotificationsEnabled(UUID userId,
                                               Boolean notificationsEnabled) {
        log.debug("Updating notifications enabled for user: {} to: {}", userId, notificationsEnabled);
        validateUserId(userId);
        validateBooleanValue(notificationsEnabled, "notifications enabled");
        
        Settings settings = getSettingsOrThrow(userId);
        settings.setNotificationsEnabled(notificationsEnabled);
        
        return settingsService.update(settings);
    }

    @Override
    @Transactional
    public Settings updateEmailNotifications(UUID userId, Boolean emailNotifications) {
        log.debug("Updating email notifications for user: {} to: {}", userId, emailNotifications);
        validateUserId(userId);
        validateBooleanValue(emailNotifications, "email notifications");
        
        Settings settings = getSettingsOrThrow(userId);
        settings.setEmailNotifications(emailNotifications);
        
        return settingsService.update(settings);
    }

    @Override
    @Transactional
    public Settings updateWeeklyReport(UUID userId, Boolean weeklyReport) {
        log.debug("Updating weekly report for user: {} to: {}", userId, weeklyReport);
        validateUserId(userId);
        validateBooleanValue(weeklyReport, "weekly report");
        
        Settings settings = getSettingsOrThrow(userId);
        settings.setWeeklyReport(weeklyReport);
        
        return settingsService.update(settings);
    }

    private Settings getSettingsOrThrow(UUID userId) {
        return settingsService.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Settings not found for user: " + userId));
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private void validateBooleanValue(Boolean value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
    }
}
