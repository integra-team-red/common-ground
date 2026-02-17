package cloudflight.integra.backend.user.model;

import cloudflight.integra.backend.user.Role;

public record UserDto(String username, Role role) {
}
