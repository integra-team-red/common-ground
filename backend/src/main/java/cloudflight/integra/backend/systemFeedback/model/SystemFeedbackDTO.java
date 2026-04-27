package cloudflight.integra.backend.systemFeedback.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.UUID;

public record SystemFeedbackDTO(
    UUID id,
    @Email(message = "The email for the feedback must be a valid feedback.")
    String email,
    @NotBlank(message = "The feedback message is required. It cannot be blank.")
    String message,
    LocalDateTime createdAt
) {
}
