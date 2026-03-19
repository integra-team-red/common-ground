package cloudflight.integra.backend.hobbyGroup.model;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public record HobbyGroupDto(
    UUID id,
    String name,
    String description,
    double radiusKm,
    List<Long> tagIds,
    UUID ownerID,
    Set<UUID> memberIds
) {
}
