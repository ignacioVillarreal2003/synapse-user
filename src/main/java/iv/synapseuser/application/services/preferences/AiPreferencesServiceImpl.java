package iv.synapseuser.application.services.preferences;

import iv.synapseuser.domain.constants.AiModel;
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
public class AiPreferencesServiceImpl implements AiPreferencesService {

    private final PreferencesService preferencesService;

    @Override
    @Transactional
    public Preferences updateAiPreferences(UUID userId, String aiModel, Double aiTemperature, String aiContext) {
        log.debug("Updating all AI preferences for user: {}", userId);
        validateUserId(userId);

        Preferences preferences = getPreferencesOrThrow(userId);

        if (aiModel != null) {
            validateAiModel(AiModel.valueOf(aiModel));
            preferences.setAiModel(AiModel.valueOf(aiModel));
        }

        if (aiTemperature != null) {
            validateAiTemperature(aiTemperature);
            preferences.setAiTemperature(aiTemperature);
        }

        if (aiContext != null) {
            validateAiContext(aiContext);
            preferences.setAiContext(aiContext);
        }

        return preferencesService.update(preferences);
    }

    @Override
    @Transactional
    public Preferences updateAiModel(UUID userId, AiModel aiModel) {
        log.debug("Updating AI model for user: {} to: {}", userId, aiModel);
        validateUserId(userId);
        validateAiModel(aiModel);
        
        Preferences preferences = getPreferencesOrThrow(userId);
        preferences.setAiModel(aiModel);
        
        return preferencesService.update(preferences);
    }

    private void validateAiModel(AiModel aiModel) {
        if (aiModel == null) {
            throw new IllegalArgumentException("AI model cannot be null");
        }
    }

    @Override
    @Transactional
    public Preferences updateAiTemperature(UUID userId, Double aiTemperature) {
        log.debug("Updating AI temperature for user: {} to: {}", userId, aiTemperature);
        validateUserId(userId);
        validateAiTemperature(aiTemperature);
        
        Preferences preferences = getPreferencesOrThrow(userId);
        preferences.setAiTemperature(aiTemperature);
        
        return preferencesService.update(preferences);
    }

    @Override
    @Transactional
    public Preferences updateAiContext(UUID userId, String aiContext) {
        log.debug("Updating AI context for user: {}", userId);
        validateUserId(userId);
        validateAiContext(aiContext);
        
        Preferences preferences = getPreferencesOrThrow(userId);
        preferences.setAiContext(aiContext);
        
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

    private void validateAiTemperature(Double aiTemperature) {
        if (aiTemperature == null) {
            throw new IllegalArgumentException("AI temperature cannot be null");
        }
        if (aiTemperature < 0.0 || aiTemperature > 1.0) {
            throw new IllegalArgumentException("AI temperature must be between 0.0 and 1.0");
        }
    }

    private void validateAiContext(String aiContext) {
        if (aiContext != null && aiContext.trim().isEmpty()) {
            throw new IllegalArgumentException("AI context cannot be empty");
        }
        if (aiContext != null && aiContext.length() > 1000) {
            throw new IllegalArgumentException("AI context cannot exceed 1000 characters");
        }
    }
}
