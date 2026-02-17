package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import cloudflight.integra.backend.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EventService {
    private final EventRepository repository;

    public EventService(EventRepository repository) {
        this.repository = repository;
    }

    public List<Event> getAll() {
        return repository.findAll();
    }

    public List<Event> getAllByTime(LocalDateTime after) {
        return repository.findAllByStartTimeAfter(after);
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

    public List<Event> getByLocationId(UUID locationId) {
        if (repository.findAllByLocationId(locationId).isEmpty()) {
            throw new ObjectNotFoundException("There are no events held at this location.");
        }
        return repository.findAllByLocationId(locationId);
    }
}
