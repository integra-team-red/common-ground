package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.exceptions.ObjectNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public Page<Event> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<Event> getAllByTime(LocalDateTime after, Pageable pageable) {
        return repository.findAllByStartTimeAfter(after, pageable);
    }

    public Optional<Event> getById(UUID id) {
        if (repository.findById(id).isEmpty()) {
            throw new ObjectNotFoundException("Event not found");
        }
        return repository.findById(id);
    }

    public Event create(Event event) {
        return repository.save(event);
    }

    public Optional<Event> update(UUID id, Event event) {

        if (repository.findById(id).isPresent()) {
            event.setId(id);
            return Optional.of(repository.save(event));
        } else throw new ObjectNotFoundException("Event not found");
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Event> getByLocationId(UUID locationId, Pageable pageable) {
        Page<Event> events = repository.findAllByLocationId(locationId, pageable);
        if(events.isEmpty()){
            throw new ObjectNotFoundException("There is not Event found at this location.");
        }
        return events;
    }
}
