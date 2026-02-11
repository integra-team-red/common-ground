package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HobbyGroupService {
    private final HobbyGroupRepository repository;

    public HobbyGroupService(HobbyGroupRepository repository) {
        this.repository = repository;
    }

    public List<HobbyGroup> getAll() {
        return repository.findAll();
    }

    public Optional<HobbyGroup> getById(UUID id) {
        return repository.findById(id);
    }

    public HobbyGroup create(HobbyGroup group) {
        return repository.save(group);
    }

    public Optional<HobbyGroup> update(UUID id, HobbyGroup group) {
        if (repository.findById(id).isPresent()) {
            group.setId(id);
            return Optional.of(repository.save(group));
        }
        return Optional.empty();
    }

    public boolean delete(UUID id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<HobbyGroup> filterByName(String containedString) {
        return repository.findAll()
            .stream()
            .filter(group -> group.getName() != null &&
                group.getName().toLowerCase().contains(containedString.toLowerCase()))
            .collect(Collectors.toList());
    }

}
