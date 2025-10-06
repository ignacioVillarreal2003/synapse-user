package iv.synapseuser.infrastructure.persistence.repositories;

import iv.synapseuser.domain.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByAuthId(UUID authId);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByAuthId(UUID authId);
    
    @Query("SELECT u FROM User u WHERE u.deletedAt IS NOT NULL AND u.id = :userId")
    boolean isDeleted(@Param("userId") UUID userId);
    
    @Query("SELECT u FROM User u WHERE u.lastActiveAt IS NULL OR u.lastActiveAt < :since")
    List<User> findInactiveSince(@Param("since") LocalDateTime since);
    
    @Query("SELECT u FROM User u WHERE u.lastActiveAt IS NOT NULL AND u.lastActiveAt >= :since")
    List<User> findAllActive(@Param("since") LocalDateTime since);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.lastActiveAt IS NOT NULL AND u.lastActiveAt >= :since")
    long countActive(@Param("since") LocalDateTime since);
    
    @Modifying
    @Query("UPDATE User u SET u.lastActiveAt = :lastActiveAt WHERE u.id = :userId")
    void updateLastActive(@Param("userId") UUID userId, @Param("lastActiveAt") LocalDateTime lastActiveAt);
    
    @Modifying
    @Query("UPDATE User u SET u.deletedAt = :deletedAt WHERE u.id = :userId")
    void softDelete(@Param("userId") UUID userId, @Param("deletedAt") LocalDateTime deletedAt);
    
    @Modifying
    @Query("UPDATE User u SET u.deletedAt = NULL WHERE u.id = :userId")
    void restore(@Param("userId") UUID userId);
}
