package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;
import cloudflight.integra.backend.matrix.api.MatrixRoomCreationRestClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestClientException;

@Service
public class EventService {
    private final EventRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);
    private final MatrixRoomCreationRestClient matrixRoomCreationRestClient;

    public EventService(EventRepository repository, MatrixRoomCreationRestClient matrixRoomCreationRestClient) {
        this.repository = repository;
        this.matrixRoomCreationRestClient = matrixRoomCreationRestClient;
    }

    @Transactional(readOnly = true)
    public Page<Event> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Event> getAllByTime(LocalDateTime after, Pageable pageable) {
        return repository.findAllByStartTimeAfter(after, pageable);
    }

    @Transactional(readOnly = true)
    public Event getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public Event create(Event event) {
        event.setId(null);
        Event newEvent = repository.save(event);
        try {
            newEvent.setMatrixRoomId(matrixRoomCreationRestClient.createRoom(event.getTitle()));
        }
        catch ( RestClientException e){
            logger.error("Failed to create Matrix room for event {}: {}", event.getTitle(), e.getMessage());
        }
        return newEvent;
    }

    @Transactional
    public Event update(UUID id, Event event) {

        Event oldEvent = repository.findById(id).orElseThrow();

        oldEvent.setEndTime(event.getEndTime());
        oldEvent.setStartTime(event.getStartTime());
        oldEvent.setHobbyGroup(event.getHobbyGroup());
        oldEvent.setLocation(event.getLocation());
        oldEvent.setTitle(event.getTitle());

        return oldEvent;
    }

    @Transactional
    public boolean delete(UUID id) {
        Event event = repository.findById(id).orElseThrow();
        repository.deleteById(event.getId());
        return true;
    }

    @Transactional(readOnly = true)
    public Page<Event> getByLocationId(UUID locationId, Pageable pageable) {
        Page<Event> events = repository.findAllByLocationId(locationId, pageable);
        if (events.isEmpty()) {
            throw new NoSuchElementException("There is not Event found at this location.");
        }
        return events;
    }
    @Transactional(readOnly = true)
    public Page<Event> getByEventTitle(String eventTitle, Pageable pageable) {
        Page<Event> events = repository.findAllByTitleContainingIgnoreCase(eventTitle, pageable);
        if (events.isEmpty()) {
            throw new NoSuchElementException("There is not Event found at this title.");
        }
        return events;
    }
}
