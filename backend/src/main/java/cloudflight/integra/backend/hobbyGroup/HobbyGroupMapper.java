package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import org.springframework.stereotype.Component;

@Component
public class HobbyGroupMapper {

    public HobbyGroupDto toDto(HobbyGroup group) {
        return new HobbyGroupDto(group.getId(), group.getName(), group.getDescription(), group.getRadiusKm());
    }

    public HobbyGroup toEntity(HobbyGroupDto groupDto) {
        return new HobbyGroup(groupDto.id(), groupDto.name(), groupDto.description(), groupDto.radiusKm());
    }

}
