package iv.synapseuser.application.services.settings;

import iv.synapseuser.domain.models.Settings;

import java.util.UUID;

public interface NotificationsSettingsService {
    Settings updateNotificationsSettings(
            UUID userId,
            Boolean notificationsEnabled,
            Boolean emailNotifications,
            Boolean weeklyReport
    );
    Settings updateNotificationsEnabled(
            UUID userId,
            Boolean notificationsEnabled);
    Settings updateEmailNotifications(
            UUID userId,
            Boolean emailNotifications);
    Settings updateWeeklyReport(
            UUID userId,
            Boolean weeklyReport);
}
