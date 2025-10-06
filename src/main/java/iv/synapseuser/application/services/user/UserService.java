package iv.synapseuser.application.services.user;

import iv.synapseuser.domain.models.User;
import iv.synapseuser.domain.requests.CreateUserRequest;
import iv.synapseuser.domain.responses.UserResponse;

import java.util.Optional;
import java.util.UUID;

public interface UserService {
    UserResponse create(CreateUserRequest request);
    UserResponse findById(UUID id);
    Optional<UserResponse> findByAuthId(UUID authId);
    Optional<UserResponse> findByEmail(String email);
    Optional<UserResponse> findByUsername(String username);
    UserResponse update(User user);
    void deleteById(UUID userId);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByAuthId(UUID authId);
}
