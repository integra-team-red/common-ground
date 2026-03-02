package cloudflight.integra.backend.hobbyGroup.model;

import java.util.Set;
import java.util.UUID;

public record HobbyGroupDto(UUID id, String name, String description, double radiusKm, UUID ownerId, Set<UUID> memberIds) {
}
