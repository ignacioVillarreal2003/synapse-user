package iv.synapseuser.domain.responses;

import iv.synapseuser.domain.constants.AiModel;
import iv.synapseuser.domain.constants.QuizDifficulty;
import iv.synapseuser.domain.models.Preferences;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PreferencesResponse {
    private UUID id;
    private AiModel aiModel;
    private Double aiTemperature;
    private String aiContext;
    private Integer flashcardsPerAnswer;
    private QuizDifficulty quizDifficulty;
    private Integer questionsPerAnswer;

    public static PreferencesResponse fromEntity(Preferences preferences) {
        return PreferencesResponse.builder()
                .id(preferences.getId())
                .aiModel(preferences.getAiModel())
                .aiTemperature(preferences.getAiTemperature())
                .aiContext(preferences.getAiContext())
                .flashcardsPerAnswer(preferences.getFlashcardsPerAnswer())
                .quizDifficulty(preferences.getQuizDifficulty())
                .questionsPerAnswer(preferences.getQuestionsPerAnswer())
                .build();
    }
}