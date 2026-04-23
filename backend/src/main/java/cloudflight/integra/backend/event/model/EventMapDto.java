package cloudflight.integra.backend.event.model;
import java.util.UUID;

public record EventMapDto(
    UUID id,
    String title,
    double latitude,
    double longitude
) {
}
