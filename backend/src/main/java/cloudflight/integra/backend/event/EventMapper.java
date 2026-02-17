package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.location.model.Location;
import cloudflight.integra.backend.exceptions.ObjectNotFoundException;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EventMapper {
    public EventDto toDto(Event event){
        return new EventDto(event.getId(), event.getTitle(), event.getStartTime(), event.getEndTime(), event.getLocation().getId(), event.getLocation().getId());
    }

    public Event toEntity(EventDto dto, Location location, Optional<HobbyGroup> hobbyGroup) {
        if (hobbyGroup.isEmpty())
            throw new ObjectNotFoundException("Hobby group was not found");
        return new Event(dto.id(), dto.title(), dto.startTime(), dto.endTime(), location, hobbyGroup.get());
    }
}
