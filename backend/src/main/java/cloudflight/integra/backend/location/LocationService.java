package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<Location> getAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Location getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public Location create(Location location) {
        return repository.save(location);
    }

    @Transactional(readOnly = true)
    public boolean delete(UUID id) {
        Location location = repository.findById(id).orElseThrow();
        repository.deleteById(location.getId());
        return true;
    }

    @Transactional(readOnly = true)
    public Page<Location> getByName(String name, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(name, pageable);
    }
}
