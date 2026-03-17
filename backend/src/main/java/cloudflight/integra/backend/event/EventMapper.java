package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.location.model.Location;
import org.springframework.stereotype.Component;


@Component
public class EventMapper {
    public EventDto toDto(Event event) {
        return new EventDto(event.getId(),
            event.getTitle(),
            event.getStartTime(),
            event.getEndTime(),
            event.getLocation().getId(),
            event.getLocation().getId());
    }

    public Event toEntity(EventDto dto, Location location, HobbyGroup hobbyGroup) {
        return new Event(dto.id(), dto.title(), dto.startTime(), dto.endTime(), location, hobbyGroup);
    }
}
