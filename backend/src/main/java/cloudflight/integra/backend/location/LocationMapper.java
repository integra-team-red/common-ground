package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.location.model.LocationDto;
import org.springframework.stereotype.Component;

@Component
public class LocationMapper {
    public LocationDto toDto(Location location) {
        return new LocationDto(location.getId(), location.getName(), location.getLatitude(), location.getLongitude());
    }

    public Location toEntity(LocationDto dto) {
        return new Location(dto.id(), dto.name(), dto.latitude(), dto.longitude());
    }
}
