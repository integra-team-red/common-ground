package cloudflight.integra.backend.user.model;

import cloudflight.integra.backend.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserDto(
    UUID id,
    @NotBlank(message = "The username is required. It cannot be blank.")
    String username,
    @Email(message = "The email is required and it must be a valid email.")
    String email,
    Role role,
    List<Long> tagIds,
    LocalDateTime joinedDate,
    String matrixUserId,
    String matrixTemporaryPassword
) {
    public UserDto(UUID id, String username, String email) {
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
