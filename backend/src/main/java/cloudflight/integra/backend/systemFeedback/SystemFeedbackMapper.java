package cloudflight.integra.backend.systemFeedback;

import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import cloudflight.integra.backend.systemFeedback.model.SystemFeedbackDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SystemFeedbackMapper {
    SystemFeedbackDTO toDto(SystemFeedback systemFeedback);

    SystemFeedback toEntity(SystemFeedbackDTO dto);
}
