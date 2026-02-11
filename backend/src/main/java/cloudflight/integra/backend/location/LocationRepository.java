package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class LocationRepository {
    private final Map<UUID, Location> locations = new HashMap<>();


    public Location save(Location location) {
        if (location.getId() == null) {
            location.setId(UUID.randomUUID());
        }
        locations.put(location.getId(), location);
        return location;
    }

    public List<Location> getAll() {
        return new ArrayList<>(locations.values());
    }

    public Optional<Location> getById(UUID id) {
        return Optional.ofNullable(locations.get(id));
    }

    public void deleteById(UUID id) {
        locations.remove(id);
    }
}
