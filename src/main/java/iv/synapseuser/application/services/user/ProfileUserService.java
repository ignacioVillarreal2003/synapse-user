package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.constants.Language;
import iv.synapseuser.domain.models.User;

import java.util.UUID;

public interface ProfileUserService {
    User updateUsername(UUID userId, String newUsername);
    User updateLanguage(UUID userId, Language language);
    User updateAvatarColor(UUID userId, String color);
}
