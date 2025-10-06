package iv.synapseuser.domain.requests;

import iv.synapseuser.domain.constants.QuizDifficulty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record UpdateContentPreferencesRequest(
        @Min(value = 1, message = "Flashcards per answer must be between 1 and 50")
        @Max(value = 50, message = "Flashcards per answer must be between 1 and 50")
        Integer flashcardsPerAnswer,

        QuizDifficulty quizDifficulty,

        @Min(value = 1, message = "Questions per answer must be between 1 and 20")
        @Max(value = 20, message = "Questions per answer must be between 1 and 20")
        Integer questionsPerAnswer
) {
}
