package cloudflight.integra.backend.user;

import cloudflight.integra.backend.tag.model.Tag;
import cloudflight.integra.backend.user.model.User;
import cloudflight.integra.backend.user.model.UserDto;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDto toDto(User user, String matrixUserId, String matrixTemporaryPassword){
        return new UserDto(user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            user.getTags().stream().map(Tag::getId).collect(Collectors.toList()),
            user.getJoinedDate(),
            matrixUserId,
            matrixTemporaryPassword);
    }

}
