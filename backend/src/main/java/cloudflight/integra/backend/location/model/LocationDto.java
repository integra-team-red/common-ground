package cloudflight.integra.backend.location.model;

import cloudflight.integra.backend.user.model.User;

import java.util.UUID;

public record LocationDto(
    UUID id,
    String name,
    Double latitude,
    Double longitude,
    User creator
) {
}
