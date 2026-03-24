package cloudflight.integra.backend.user.model;

import cloudflight.integra.backend.user.Role;

import java.util.List;
import java.util.UUID;

public record UserDto(UUID id, String username, String email, Role role, List<Long> tagIds) {
    public UserDto(UUID id ,String username, String email) {
        this(id, username, email,  null, null);
    }
}
