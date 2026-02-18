package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.UUID;

@Service
public class HobbyGroupService {
    private final HobbyGroupRepository repository;


    public HobbyGroupService(HobbyGroupRepository repository) {
        this.repository = repository;

    }

    public Page<HobbyGroup> getAll(Pageable pageable) {
        return repository.findAll(pageable);
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

    public Page<HobbyGroup> filterByName(String containedString, Pageable pageable) {
        return repository.findByNameContainingIgnoreCase(containedString, pageable);
    }

}
