package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.model.Location;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {
    @Mapping(target = "locationId", source = "location.id")
    @Mapping(target = "hobbyGroupId", source = "hobbyGroup.id")
    EventDto toDto(Event event);

    @Mapping(target = "id", source = "dto.id")
    Event toEntity(EventDto dto, Location location, HobbyGroup hobbyGroup);
}
