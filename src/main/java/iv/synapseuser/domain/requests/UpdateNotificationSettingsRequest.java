package iv.synapseuser.domain.requests;

public record UpdateNotificationSettingsRequest(
        Boolean notificationsEnabled,
        Boolean emailNotifications,
        Boolean weeklyReport
) {
}
