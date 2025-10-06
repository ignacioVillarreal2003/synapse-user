package iv.synapseuser.domain.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record UpdateAiPreferencesRequest(
        @Size(max = 50, message = "Ai model must not exceed 50 characters")
        String aiModel,

        @Min(value = 0, message = "Ai temperature must be between 0 and 1")
        @Max(value = 1, message = "Ai temperature must be between 0 and 1")
        Double aiTemperature,

        @Size(max = 1000, message = "Ai context must not exceed 1000 characters")
        String aiContext
) {
}
