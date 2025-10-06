package iv.synapseuser.domain.models;

import iv.synapseuser.domain.constants.AiModel;
import iv.synapseuser.domain.constants.QuizDifficulty;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "preferences")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Preferences {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, nullable = false, name = "id")
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "ai_model", length = 50)
    @Builder.Default
    private AiModel aiModel = AiModel.MODEL_1;

    @Column(name = "ai_temperature", nullable = false)
    @Builder.Default
    private Double aiTemperature = 0.7;

    @Column(name = "ai_context", length = 1000)
    private String aiContext;

    @Column(name = "flashcards_per_answer", nullable = false)
    @Builder.Default
    private Integer flashcardsPerAnswer = 10;

    @Enumerated(EnumType.STRING)
    @Column(name = "quiz_difficulty", nullable = false, length = 20)
    @Builder.Default
    private QuizDifficulty quizDifficulty = QuizDifficulty.MIXED;

    @Column(name = "questions_per_answer", nullable = false)
    @Builder.Default
    private Integer questionsPerAnswer = 10;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, updatable = false, nullable = false)
    private User user;
}
