package cloudflight.integra.backend.hobbyGroup.model;

import cloudflight.integra.backend.user.model.UserSummaryDto;

import java.util.List;
import java.util.UUID;

public record HobbyGroupDto(
    UUID id,
    String name,
    String description,
    double radiusKm,
    List<Long> tagIds,
    UserSummaryDto owner,
    List<UUID> memberIds
) {
}
