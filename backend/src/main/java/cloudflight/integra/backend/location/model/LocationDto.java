package cloudflight.integra.backend.location.model;

import cloudflight.integra.backend.user.model.UserDto;

import java.util.UUID;

public record LocationDto(
    UUID id,
    String name,
    Double latitude,
    Double longitude,
    UserDto creator
) {
}
