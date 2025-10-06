package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.models.User;
import iv.synapseuser.infrastructure.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityUserServiceImpl implements ActivityUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void updateLastActive(UUID userId) {
        log.debug("Updating last active time for user: {}", userId);
        validateUserId(userId);
        
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        userRepository.updateLastActive(userId, LocalDateTime.now());
    }

    @Override
    public List<User> findInactiveSince(LocalDateTime since) {
        log.debug("Finding users inactive since: {}", since);
        validateSince(since);
        return userRepository.findInactiveSince(since);
    }

    @Override
    public List<User> findAllActive() {
        log.debug("Finding all active users");
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return userRepository.findAllActive(thirtyDaysAgo);
    }

    @Override
    public long countActive() {
        log.debug("Counting active users");
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        return userRepository.countActive(thirtyDaysAgo);
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    private void validateSince(LocalDateTime since) {
        if (since == null) {
            throw new IllegalArgumentException("Since date cannot be null");
        }
        if (since.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("Since date cannot be in the future");
        }
    }
}
