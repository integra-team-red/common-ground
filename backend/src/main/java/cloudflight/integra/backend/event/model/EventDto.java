package cloudflight.integra.backend.event.model;


import java.time.LocalDateTime;
import java.util.UUID;

public record EventDto(UUID id, String title, LocalDateTime startTime, LocalDateTime endTime, UUID locationId, UUID hobbyGroupId) {}
