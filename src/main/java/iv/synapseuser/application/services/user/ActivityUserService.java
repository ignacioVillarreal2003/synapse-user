package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.models.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ActivityUserService {
    void updateLastActive(UUID userId);
    List<User> findInactiveSince(LocalDateTime since);
    List<User> findAllActive();
    long countActive();
}
