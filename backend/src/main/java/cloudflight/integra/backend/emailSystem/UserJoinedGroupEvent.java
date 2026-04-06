package cloudflight.integra.backend.emailSystem;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.user.model.User;

public record UserJoinedGroupEvent(User user, HobbyGroup group) {
}
