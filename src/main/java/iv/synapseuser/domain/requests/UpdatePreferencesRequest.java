package iv.synapseuser.domain.requests;

import iv.synapseuser.domain.constants.QuizDifficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdatePreferencesRequest(
        @Size(max = 50, message = "Ai model must not exceed 50 characters")
        String aiModel,

        @Min(value = 0, message = "Ai temperature must be between 0 and 1")
        @Max(value = 1, message = "Ai temperature must be between 0 and 1")
        Double aiTemperature,

        @Size(max = 1000, message = "Ai context must not exceed 1000 characters")
        String aiContext,

        @Min(value = 1, message = "Flashcards per answer must be between 1 and 50")
        @Max(value = 50, message = "Flashcards per answer must be between 1 and 50")
        Integer flashcardsPerAnswer,

        QuizDifficulty quizDifficulty,

        @Min(value = 1, message = "Questions per answer must be between 1 and 20")
        @Max(value = 20, message = "Questions per answer must be between 1 and 20")
        Integer questionsPerAnswer
) {
}
