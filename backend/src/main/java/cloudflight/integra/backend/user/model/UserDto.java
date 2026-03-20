package cloudflight.integra.backend.user.model;

import cloudflight.integra.backend.user.Role;

import java.util.List;

public record UserDto(String username, String email, String password, Role role, List<Long> tagIds) {
    public UserDto(String username, String email, String password) {
        this(username, email, password, null, null);
    }
}
