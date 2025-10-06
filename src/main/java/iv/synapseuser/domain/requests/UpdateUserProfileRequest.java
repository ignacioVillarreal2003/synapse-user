package iv.synapseuser.domain.requests;

import iv.synapseuser.domain.constants.Language;
import jakarta.validation.constraints.Size;

public record UpdateUserProfileRequest(
        @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
        String username,

        @Size(max = 8)
        String profileAvatarColor,

        Language language
) {
}
