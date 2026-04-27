package cloudflight.integra.backend.hobbyGroup.model;

import cloudflight.integra.backend.location.model.Location;

import java.util.List;
import java.util.UUID;

public record HobbyGroupDto(
    UUID id,
    String name,
    String description,
    double radiusKm,
    List<Long> tagIds,
    UUID ownerID,
    List<UUID> memberIds,
    UUID groupLocationId
) {
}
