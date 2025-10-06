package iv.synapseuser.application.services.preferences;

import iv.synapseuser.domain.constants.QuizDifficulty;
import iv.synapseuser.domain.models.Preferences;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentPreferencesServiceImpl implements ContentPreferencesService {

    private final PreferencesService preferencesService;

    @Override
    @Transactional
    public Preferences updateContentPreferences(UUID userId, Integer flashcardsPerAnswer,
                                                QuizDifficulty quizDifficulty, Integer questionsPerAnswer) {
        log.debug("Updating all content preferences for user: {}", userId);
        validateUserId(userId);

        Preferences preferences = getPreferencesOrThrow(userId);

        if (flashcardsPerAnswer != null) {
            validateFlashcardsPerAnswer(flashcardsPerAnswer);
            preferences.setFlashcardsPerAnswer(flashcardsPerAnswer);
        }

        if (quizDifficulty != null) {
            validateQuizDifficulty(quizDifficulty);
            preferences.setQuizDifficulty(quizDifficulty);
        }

        if (questionsPerAnswer != null) {
            validateQuestionsPerAnswer(questionsPerAnswer);
            preferences.setQuestionsPerAnswer(questionsPerAnswer);
        }

        return preferencesService.update(preferences);
    }

    @Override
    @Transactional
    public Preferences updateFlashcardsPerAnswer(UUID userId, Integer flashcardsPerAnswer) {
        log.debug("Updating flashcards per answer for user: {} to: {}", userId, flashcardsPerAnswer);
        validateUserId(userId);
        validateFlashcardsPerAnswer(flashcardsPerAnswer);
        
        Preferences preferences = getPreferencesOrThrow(userId);
        preferences.setFlashcardsPerAnswer(flashcardsPerAnswer);
        
        return preferencesService.update(preferences);
    }

    @Override
    @Transactional
    public Preferences updateQuizDifficulty(UUID userId, QuizDifficulty quizDifficulty) {
        log.debug("Updating quiz difficulty for user: {} to: {}", userId, quizDifficulty);
        validateUserId(userId);
        validateQuizDifficulty(quizDifficulty);
        
        Preferences preferences = getPreferencesOrThrow(userId);
        preferences.setQuizDifficulty(quizDifficulty);
        
        return preferencesService.update(preferences);
    }

    @Override
    @Transactional
    public Preferences updateQuestionsPerAnswer(UUID userId, Integer questionsPerAnswer) {
        log.debug("Updating questions per answer for user: {} to: {}", userId, questionsPerAnswer);
        validateUserId(userId);
        validateQuestionsPerAnswer(questionsPerAnswer);
        
        Preferences preferences = getPreferencesOrThrow(userId);
        preferences.setQuestionsPerAnswer(questionsPerAnswer);
        
        return preferencesService.update(preferences);
    }

    private Preferences getPreferencesOrThrow(UUID userId) {
        return preferencesService.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Preferences not found for user: " + userId));
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private void validateFlashcardsPerAnswer(Integer flashcardsPerAnswer) {
        if (flashcardsPerAnswer == null) {
            throw new IllegalArgumentException("Flashcards per answer cannot be null");
        }
        if (flashcardsPerAnswer < 1 || flashcardsPerAnswer > 50) {
            throw new IllegalArgumentException("Flashcards per answer must be between 1 and 50");
        }
    }

    private void validateQuizDifficulty(QuizDifficulty quizDifficulty) {
        if (quizDifficulty == null) {
            throw new IllegalArgumentException("Quiz difficulty cannot be null");
        }
    }

    private void validateQuestionsPerAnswer(Integer questionsPerAnswer) {
        if (questionsPerAnswer == null) {
            throw new IllegalArgumentException("Questions per answer cannot be null");
        }
        if (questionsPerAnswer < 1 || questionsPerAnswer > 20) {
            throw new IllegalArgumentException("Questions per answer must be between 1 and 20");
        }
    }
}
