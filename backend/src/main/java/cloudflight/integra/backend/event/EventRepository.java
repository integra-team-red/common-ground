package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class EventRepository {
    private final Map<UUID, Event> events = new HashMap<>();

    public List<Event> findAll(){
        return new ArrayList<>(events.values());
    }
    public Optional<Event> findById(UUID id){
        return Optional.ofNullable(events.get(id));
    }
    public Event save(Event event){
        if(event.getId() == null){
            event.setId(UUID.randomUUID());
        }
        events.put(event.getId(), event);
        return event;
    }

    public boolean deleteById(UUID id){
        return events.remove(id) != null;
    }
}
