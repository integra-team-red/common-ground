package cloudflight.integra.backend.user.model;

import cloudflight.integra.backend.user.Role;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDto(
    UUID id,
    String username,
    String email,
    Role role,
    List<Long> tagIds,
    LocalDateTime joinedDate,
    String matrixUserId,
    String matrixTemporaryPassword
) {
    public UserDto(UUID id ,String username, String email) {
        this(id,
            username,
            email,
            null,
            null,
            null,
            null,
            null);
    }
}
