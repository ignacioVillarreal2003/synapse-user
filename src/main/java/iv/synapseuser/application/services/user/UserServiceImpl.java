package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.models.User;
import iv.synapseuser.domain.requests.CreateUserRequest;
import iv.synapseuser.domain.responses.UserResponse;
import iv.synapseuser.infrastructure.persistence.repositories.UserRepository;
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
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        log.debug("Creating user: {}", request);
        validateCreateUserRequest(request);

        if (existsByAuthId(request.authId())) {
            throw new IllegalArgumentException("User already exists");
        }

        User user = userRepository.save(
                User.builder()
                        .email(request.email())
                        .authId(request.authId())
                        .build()
        );

        return UserResponse.fromEntity(user);
    }

    private void validateCreateUserRequest(CreateUserRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (request.email() == null || request.email().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (request.authId() == null) {
            throw new IllegalArgumentException("Auth ID cannot be null");
        }
    }

    @Override
    public UserResponse findById(UUID id) {
        log.debug("Finding user by id: {}", id);

        User user = userRepository.findById(id).orElse(null);

        if (user != null) {
            return UserResponse.fromEntity(user);
        }

        return null;
    }

    @Override
    public Optional<User> findByAuthId(UUID authId) {
        log.debug("Finding user by auth id: {}", authId);
        return userRepository.findByAuthId(authId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        log.debug("Finding user by email: {}", email);
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        log.debug("Finding user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public User update(User user) {
        log.debug("Updating user: {}", user);
        validateUser(user);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(UUID userId) {
        log.debug("Deleting user by id: {}", userId);
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        userRepository.deleteById(userId);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByAuthId(UUID authId) {
        return userRepository.existsByAuthId(authId);
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        if (user.getAuthId() == null) {
            throw new IllegalArgumentException("Auth ID cannot be null");
        }
        if (user.getLanguage() == null) {
            throw new IllegalArgumentException("Language cannot be null");
        }
        if (user.getEmail().length() > 255) {
            throw new IllegalArgumentException("Email cannot exceed 255 characters");
        }
        if (user.getUsername() != null && user.getUsername().length() > 50) {
            throw new IllegalArgumentException("Username cannot exceed 50 characters");
        }
        if (user.getProfileAvatarColor() != null && user.getProfileAvatarColor().length() > 8) {
            throw new IllegalArgumentException("Profile avatar color cannot exceed 8 characters");
        }
    }
}
