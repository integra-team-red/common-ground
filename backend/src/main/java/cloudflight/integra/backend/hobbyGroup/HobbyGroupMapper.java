package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import cloudflight.integra.backend.user.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class HobbyGroupMapper {

    public HobbyGroupDto toDto(HobbyGroup group) {
        return new HobbyGroupDto(group.getId(),
            group.getName(),
            group.getDescription(),
            group.getRadiusKm(),
            group.getOwner().getId(),
            group.getMembers().stream().map(User::getId).collect(Collectors.toSet()));
    }

    public HobbyGroup toEntity(HobbyGroupDto groupDto, User owner) {
        return new HobbyGroup(groupDto.id(),
            groupDto.name(),
            groupDto.description(),
            groupDto.radiusKm(),
            owner
            );
    }

}
