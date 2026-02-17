package cloudflight.integra.backend.event;

import cloudflight.integra.backend.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
    Page<Event> findAllByStartTimeAfter(LocalDateTime after, Pageable pageable);

    Page<Event> findAllByLocationId(UUID locationId, Pageable pageable);
}
