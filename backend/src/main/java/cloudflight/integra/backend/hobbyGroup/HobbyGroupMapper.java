package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroupDto;
import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface HobbyGroupMapper {
    @Mapping(target = "ownerID", source = "group.owner.id")
    @Mapping(target = "memberIds", source = "group.members")
    HobbyGroupDto toDto(HobbyGroup group, List<Long> tagIds);

    @Mapping(target = "id", source = "groupDto.id")
    @Mapping(target = "tags", source = "tags")
    @Mapping(target = "owner", source = "owner")
    @Mapping(target = "members", source = "owner")
    HobbyGroup toEntity(HobbyGroupDto groupDto, List<Tag> tags, User owner);

    default UUID mapMember(User member) {
        return member.getId();
    }

    default List<User> mapOwnerToMembers(User owner) {
        return owner == null ? List.of() : List.of(owner);
    }
}
