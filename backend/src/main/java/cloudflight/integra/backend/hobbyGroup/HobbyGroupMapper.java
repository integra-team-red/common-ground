package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import cloudflight.integra.backend.tag.model.Tag;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HobbyGroupMapper {

    public HobbyGroupDto toDto(HobbyGroup group, List<Long> tagIds) {
        return new HobbyGroupDto(group.getId(), group.getName(), group.getDescription(), group.getRadiusKm(), tagIds);
    }

    public HobbyGroup toEntity(HobbyGroupDto groupDto, List<Tag> tags) {
        return new HobbyGroup(groupDto.id(), groupDto.name(), groupDto.description(), groupDto.radiusKm(), tags);
    }

}
