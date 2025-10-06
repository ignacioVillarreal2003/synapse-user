package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.models.User;
import iv.synapseuser.infrastructure.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeletionUserServiceImpl implements DeletionUserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void softDelete(UUID userId) {
        log.debug("Soft deleting user: {}", userId);
        validateUserId(userId);
        
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        if (userRepository.isDeleted(userId)) {
            log.warn("User {} is already soft deleted", userId);
            return;
        }
        
        userRepository.softDelete(userId, LocalDateTime.now());
    }

    @Override
    @Transactional
    public User restore(UUID userId) {
        log.debug("Restoring user: {}", userId);
        validateUserId(userId);
        
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        if (!userRepository.isDeleted(userId)) {
            log.warn("User {} is not deleted, cannot restore", userId);
            return userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        }
        
        userRepository.restore(userId);
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    @Override
    @Transactional
    public void hardDelete(UUID userId) {
        log.debug("Hard deleting user: {}", userId);
        validateUserId(userId);
        
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        userRepository.deleteById(userId);
    }

    @Override
    public boolean isDeleted(UUID userId) {
        log.debug("Checking if user is deleted: {}", userId);
        validateUserId(userId);
        
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with id: " + userId);
        }
        
        return userRepository.isDeleted(userId);
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }
}
