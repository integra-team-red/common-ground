package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.EventDto;
import cloudflight.integra.backend.event.model.Event;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventDto toDto(Event event){
        return new EventDto(event.getId(), event.getTitle(), event.getStartTime(), event.getEndTime());
    }

    public Event toEntity(EventDto dto){
        return new Event(dto.id(), dto.title(),  dto.startTime(), dto.endTime());
    }
}
