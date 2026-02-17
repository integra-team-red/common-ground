package cloudflight.integra.backend.location;

import cloudflight.integra.backend.location.model.Location;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LocationService {
    private final LocationRepository repository;

    public LocationService(LocationRepository repository) {
        this.repository = repository;
    }

    public List<Location> getAll() {
        return repository.findAll();
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


    public List<Location> getByName(String name) {
        return getAll().stream().filter(x -> x.getName().toLowerCase().contains(name.toLowerCase())).toList();
    }
}
