package iv.synapseuser.domain.responses;

import iv.synapseuser.domain.constants.Language;
import iv.synapseuser.domain.models.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private UUID id;
    private UUID authId;
    private String email;
    private String username;
    private String profileAvatarColor;
    private Language language;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .authId(user.getAuthId())
                .email(user.getEmail())
                .username(user.getUsername())
                .profileAvatarColor(user.getProfileAvatarColor())
                .language(user.getLanguage())
                .build();
    }
}