package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public Page<Location> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Optional<Location> getById(UUID id) {
        return repository.findById(id);
    }

    public Location create(Location location) {
        return repository.save(location);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public Page<Location> getByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
}
