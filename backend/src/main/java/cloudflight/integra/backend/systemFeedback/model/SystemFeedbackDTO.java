package cloudflight.integra.backend.systemFeedback.model;

import java.time.LocalDateTime;
import java.util.UUID;

public record SystemFeedbackDTO(UUID id, String email, String message, LocalDateTime createdAt) {
}
