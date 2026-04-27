package cloudflight.integra.backend.event.model;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(
    UUID id,
    @NotBlank(message = "The event title is required. It cannot be blank.")
    String title,
    @Future(message = "The start date of the event must be in the future. It cannot be in the past.")
    LocalDateTime startTime,
    @Future(message = "The end date of the event must be in the future. It cannot be in the past.")
    LocalDateTime endTime,
    @NotNull(message = "The location is required.")
    UUID locationId,
    @NotNull(message = "The hobby group is required.")
    UUID hobbyGroupId,
    String matrixRoomId
) {
}
