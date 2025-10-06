package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.models.User;

import java.util.UUID;

public interface DeletionUserService {
    void softDelete(UUID userId);
    User restore(UUID userId);
    void hardDelete(UUID userId);
    boolean isDeleted(UUID userId);
}
