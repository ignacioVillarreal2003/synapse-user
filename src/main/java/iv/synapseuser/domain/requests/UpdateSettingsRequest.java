package iv.synapseuser.domain.requests;

import iv.synapseuser.domain.constants.Theme;

public record UpdateSettingsRequest(
        Theme theme,

        Boolean notificationsEnabled,

        Boolean emailNotifications,

        Boolean weeklyReport
) {
}
