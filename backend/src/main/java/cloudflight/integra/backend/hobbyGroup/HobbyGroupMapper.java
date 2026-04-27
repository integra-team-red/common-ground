package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import cloudflight.integra.backend.user.model.UserSummaryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class HobbyGroupMapper {

    public HobbyGroupDto toDto(HobbyGroup group, List<Long> tagIds) {
        return new HobbyGroupDto(group.getId(),
            group.getName(),
            group.getDescription(),
            group.getRadiusKm(),
            tagIds,
            new UserSummaryDto(
                group.getOwner().getId(),
                group.getOwner().getUsername()
            ),
            group.getMembers().stream().map(User::getId).collect(Collectors.toList()));
    }

    public HobbyGroup toEntity(HobbyGroupDto groupDto, List<Tag> tags, User owner) {
        return new HobbyGroup(groupDto.id(),
            groupDto.name(),
            groupDto.description(),
            groupDto.radiusKm(),
            tags,
            owner);
    }

}
