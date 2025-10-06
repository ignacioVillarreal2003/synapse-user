package iv.synapseuser.domain.responses;

import iv.synapseuser.domain.constants.AiModel;
import iv.synapseuser.domain.constants.Language;
import iv.synapseuser.domain.constants.QuizDifficulty;
import iv.synapseuser.domain.constants.Theme;
import iv.synapseuser.domain.models.Preferences;
import iv.synapseuser.domain.models.Settings;
import iv.synapseuser.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCompleteResponse {
    private UUID id;
    private UUID authId;
    private String email;
    private String username;
    private String profileAvatarColor;
    private Language language;
    private Theme theme;
    private Boolean notificationsEnabled;
    private Boolean emailNotifications;
    private Boolean weeklyReport;
    private AiModel aiModel;
    private Double aiTemperature;
    private String aiContext;
    private Integer flashcardsPerAnswer;
    private QuizDifficulty quizDifficulty;
    private Integer questionsPerAnswer;

    public static UserCompleteResponse fromEntities(
            User user,
            Preferences preferences,
            Settings settings) {

        return UserCompleteResponse.builder()
                .id(user.getId())
                .authId(user.getAuthId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileAvatarColor(user.getProfileAvatarColor())
                .language(user.getLanguage())
                .aiModel(preferences.getAiModel())
                .aiTemperature(preferences.getAiTemperature())
                .aiContext(preferences.getAiContext())
                .flashcardsPerAnswer(preferences.getFlashcardsPerAnswer())
                .quizDifficulty(preferences.getQuizDifficulty())
                .questionsPerAnswer(preferences.getQuestionsPerAnswer())
                .theme(settings.getTheme())
                .notificationsEnabled(settings.getNotificationsEnabled())
                .emailNotifications(settings.getEmailNotifications())
                .weeklyReport(settings.getWeeklyReport())
                .build();
    }
}