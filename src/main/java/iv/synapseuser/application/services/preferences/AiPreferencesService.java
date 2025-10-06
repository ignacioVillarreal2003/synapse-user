package iv.synapseuser.application.services.preferences;

import iv.synapseuser.domain.constants.AiModel;
import iv.synapseuser.domain.models.Preferences;

import java.util.UUID;

public interface AiPreferencesService {
    Preferences updateAiPreferences(
            UUID userId,
            String aiModel,
            Double aiTemperature,
            String aiContext);
    Preferences updateAiModel(
            UUID userId,
            AiModel aiModel);
    Preferences updateAiTemperature(
            UUID userId,
            Double aiTemperature);
    Preferences updateAiContext(
            UUID userId,
            String aiContext);
}
