package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.constants.Language;
import iv.synapseuser.domain.models.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileUserServiceImpl implements ProfileUserService {

    private final UserService userService;

    @Override
    @Transactional
    public User updateUsername(UUID userId,
                               String newUsername) {
        log.debug("Updating username for user: {} to: {}", userId, newUsername);
        validateUserId(userId);
        validateUsername(newUsername);
        
        User user = getUserOrThrow(userId);
        
        if (userService.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("Username already exists: " + newUsername);
        }
        
        user.setUsername(newUsername);
        return userService.update(user);
    }

    @Override
    @Transactional
    public User updateLanguage(UUID userId,
                               Language language) {
        log.debug("Updating language for user: {} to: {}", userId, language);
        validateUserId(userId);
        validateLanguage(language);
        
        User user = getUserOrThrow(userId);
        user.setLanguage(language);
        return userService.update(user);
    }

    @Override
    @Transactional
    public User updateAvatarColor(UUID userId,
                                  String color) {
        log.debug("Updating avatar color for user: {} to: {}", userId, color);
        validateUserId(userId);
        validateAvatarColor(color);
        
        User user = getUserOrThrow(userId);
        user.setProfileAvatarColor(color);
        return userService.update(user);
    }

    private User getUserOrThrow(UUID userId) {
        return userService.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (username.length() > 50) {
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        }
        if (username.trim().length() < 3) {
            throw new IllegalArgumentException("Username must be at least 3 characters long");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
        }
    }

    private void validateLanguage(Language language) {
        if (language == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
    }

    private void validateAvatarColor(String color) {
        if (color != null && color.trim().isEmpty()) {
            throw new IllegalArgumentException("Avatar color cannot be empty");
        }
        if (color != null && color.length() > 8) {
            throw new IllegalArgumentException("Avatar color cannot exceed 8 characters");
        }
        if (color != null && !color.matches("^#[0-9A-Fa-f]{6}$")) {
            throw new IllegalArgumentException("Avatar color must be a valid hex color (e.g., #FF5733)");
        }
    }
}
