package cloudflight.integra.backend.location.model;

import java.util.UUID;

public record LocationDto(UUID id, String name, double latitude, double longitude) {
}
