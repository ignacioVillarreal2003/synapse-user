package iv.synapseuser.application.services.preferences;

import iv.synapseuser.domain.constants.QuizDifficulty;
import iv.synapseuser.domain.models.Preferences;

import java.util.UUID;

public interface ContentPreferencesService {
    Preferences updateContentPreferences(
            UUID userId,
            Integer flashcardsPerAnswer,
            QuizDifficulty quizDifficulty,
            Integer questionsPerAnswer
    );
    Preferences updateFlashcardsPerAnswer(
            UUID userId,
            Integer flashcardsPerAnswer);
    Preferences updateQuizDifficulty(
            UUID userId,
            QuizDifficulty quizDifficulty);
    Preferences updateQuestionsPerAnswer(
            UUID userId,
            Integer questionsPerAnswer);
}
