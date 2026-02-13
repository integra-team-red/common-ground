package cloudflight.integra.backend.systemFeedback;

import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import cloudflight.integra.backend.systemFeedback.model.SystemFeedbackDTO;
import org.springframework.stereotype.Component;

@Component
public class SystemFeedbackMapper {

    public SystemFeedbackDTO toDto(SystemFeedback systemFeedback) {
        return new SystemFeedbackDTO(systemFeedback.getId(),
            systemFeedback.getEmail(),
            systemFeedback.getMessage(),
            systemFeedback.getCreatedAt());
    }

    public SystemFeedback toEntity(SystemFeedbackDTO dto) {
        return new SystemFeedback(dto.id(), dto.email(), dto.message(), dto.createdAt());
    }
}
