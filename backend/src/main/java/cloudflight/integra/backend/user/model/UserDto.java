package cloudflight.integra.backend.user.model;

import cloudflight.integra.backend.user.Role;

import java.util.List;

public record UserDto(String username, Role role, List<Long> tagIds) {
}
