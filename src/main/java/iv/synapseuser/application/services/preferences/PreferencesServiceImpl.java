package iv.synapseuser.application.services.preferences;

import iv.synapseuser.domain.constants.AiModel;
import iv.synapseuser.domain.constants.QuizDifficulty;
import iv.synapseuser.domain.models.Preferences;
import iv.synapseuser.domain.models.User;
import iv.synapseuser.infrastructure.persistence.repositories.PreferencesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PreferencesServiceImpl implements PreferencesService {

    private final PreferencesRepository preferencesRepository;

    @Override
    @Transactional
    public Preferences create(Preferences preferences) {
        log.debug("Creating preferences: {}", preferences);
        validatePreferences(preferences);
        return preferencesRepository.save(preferences);
    }

    @Override
    @Transactional
    public Preferences createDefault(UUID userId) {
        log.debug("Creating default preferences for user: {}", userId);
        
        if (existsByUserId(userId)) {
            log.warn("Preferences already exist for user: {}", userId);
            return getOrCreate(userId);
        }

        Preferences defaultPreferences = Preferences.builder()
                .aiModel(AiModel.MODEL_1)
                .aiTemperature(0.7)
                .flashcardsPerAnswer(10)
                .quizDifficulty(QuizDifficulty.MIXED)
                .questionsPerAnswer(10)
                .build();
        
        User user = User.builder().id(userId).build();
        defaultPreferences.setUser(user);
        
        return preferencesRepository.save(defaultPreferences);
    }

    @Override
    public Optional<Preferences> findById(UUID id) {
        log.debug("Finding preferences by id: {}", id);
        return preferencesRepository.findById(id);
    }

    @Override
    public Optional<Preferences> findByUserId(UUID userId) {
        log.debug("Finding preferences by user id: {}", userId);
        return preferencesRepository.findByUser_Id(userId);
    }

    @Override
    @Transactional
    public Preferences getOrCreate(UUID userId) {
        log.debug("Getting or creating preferences for user: {}", userId);
        return findByUserId(userId)
                .orElseGet(() -> createDefault(userId));
    }

    @Override
    @Transactional
    public Preferences update(Preferences preferences) {
        log.debug("Updating preferences: {}", preferences);
        validatePreferences(preferences);
        return preferencesRepository.save(preferences);
    }

    @Override
    @Transactional
    public Preferences resetToDefaults(UUID userId) {
        log.debug("Resetting preferences to defaults for user: {}", userId);
        
        Preferences preferences = findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Preferences not found for user: " + userId));
        
        preferences.setQuestionsPerAnswer(10);
        preferences.setFlashcardsPerAnswer(10);
        preferences.setQuizDifficulty(QuizDifficulty.MIXED);
        preferences.setAiContext(null);
        preferences.setAiTemperature(0.7);
        preferences.setAiModel(AiModel.MODEL_1);
        
        return preferencesRepository.save(preferences);
    }

    @Override
    @Transactional
    public void deleteByUserId(UUID userId) {
        log.debug("Deleting preferences for user: {}", userId);
        preferencesRepository.findByUser_Id(userId)
                .ifPresent(preferencesRepository::delete);
    }

    @Override
    public boolean existsByUserId(UUID userId) {
        return preferencesRepository.existsByUser_Id(userId);
    }

    private void validatePreferences(Preferences preferences) {
        if (preferences == null) {
            throw new IllegalArgumentException("Preferences cannot be null");
        }
        if (preferences.getUser() == null || preferences.getUser().getId() == null) {
            throw new IllegalArgumentException("Preferences must have a valid user");
        }
        if (preferences.getAiTemperature() != null &&
                (preferences.getAiTemperature() < 0.0 || preferences.getAiTemperature() > 1.0)) {
            throw new IllegalArgumentException("AI temperature must be between 0.0 and 1.0");
        }
        if (preferences.getFlashcardsPerAnswer() != null &&
                (preferences.getFlashcardsPerAnswer() < 1 || preferences.getFlashcardsPerAnswer() > 50)) {
            throw new IllegalArgumentException("Flashcards per answer must be between 1 and 50");
        }
        if (preferences.getQuestionsPerAnswer() != null &&
                (preferences.getQuestionsPerAnswer() < 1 || preferences.getQuestionsPerAnswer() > 20)) {
            throw new IllegalArgumentException("Questions per answer must be between 1 and 20");
        }
    }
}
