package cloudflight.integra.backend.user;

import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import cloudflight.integra.backend.user.model.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "tagIds", source = "user.tags")
    UserDto toDto(User user, String matrixUserId, String matrixTemporaryPassword);

    default Long mapTag(Tag tag) {
        return tag.getId();
    }
}
