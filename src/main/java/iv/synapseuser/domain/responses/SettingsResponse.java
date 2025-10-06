package iv.synapseuser.domain.responses;

import iv.synapseuser.domain.constants.Theme;
import iv.synapseuser.domain.models.Settings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SettingsResponse {
    private UUID id;
    private Theme theme;
    private Boolean notificationsEnabled;
    private Boolean emailNotifications;
    private Boolean weeklyReport;

    public static SettingsResponse fromEntity(Settings settings) {
        return SettingsResponse.builder()
                .id(settings.getId())
                .theme(settings.getTheme())
                .notificationsEnabled(settings.getNotificationsEnabled())
                .emailNotifications(settings.getEmailNotifications())
                .weeklyReport(settings.getWeeklyReport())
                .build();
    }
}