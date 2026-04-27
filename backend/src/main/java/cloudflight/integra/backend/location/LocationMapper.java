package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.location.model.LocationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocationMapper {
    LocationDto toDto(Location location);

    Location toEntity(LocationDto dto);
}
