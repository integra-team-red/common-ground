package cloudflight.integra.backend.hobbyGroup.model;

import cloudflight.integra.backend.user.model.UserSummaryDto;

import cloudflight.integra.backend.location.model.Location;

import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public record HobbyGroupDto(
    UUID id,
    @NotBlank(message = "The hobby group name is required. It cannot be blank.")
    String name,
    @NotBlank(message = "The hobby group description is required. It cannot be blank.")
    String description,
    double radiusKm,
    List<Long> tagIds,
    UserSummaryDto owner,
    List<UUID> memberIds,
    UUID groupLocationId
) {
}
