package iv.synapseuser.domain.models;

import iv.synapseuser.domain.constants.Language;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users", indexes = {
        @Index(name = "idx_auth_id", columnList = "auth_id"),
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_username", columnList = "username")
})@RequiredArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private UUID id;

    @Column(name = "auth_id", unique = true, nullable = false)
    private UUID authId;

    @Column(name = "email", unique = true, nullable = false, length = 255)
    private String email;

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "profile_avatar_color", length = 8)
    private String profileAvatarColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false, length = 20)
    @Builder.Default
    private Language language = Language.SPANISH;

    @Column(name = "last_active_at")
    private LocalDateTime lastActiveAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Preferences preferences;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Settings settings;
}