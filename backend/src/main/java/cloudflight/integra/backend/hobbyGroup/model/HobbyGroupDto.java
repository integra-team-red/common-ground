package cloudflight.integra.backend.hobbyGroup.model;

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
    UUID ownerID,
    List<UUID> memberIds
) {
}
