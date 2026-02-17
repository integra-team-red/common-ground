package cloudflight.integra.backend.systemFeedback;

import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class SystemFeedbackService {

    private final SystemFeedbackRepository repository;

    public SystemFeedbackService(SystemFeedbackRepository repository) {
        this.repository = repository;
    }

    public List<SystemFeedback> getAll() {
        return repository.findAll();
    }

    public Optional<SystemFeedback> getById(UUID id) {
        return repository.findById(id);
    }

    public SystemFeedback create(SystemFeedback systemFeedback) {
        if (systemFeedback.getCreatedAt() == null) {
            systemFeedback.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(systemFeedback);
    }

    public Optional<SystemFeedback> update(UUID id, SystemFeedback systemFeedback) {
        if (repository.findById(id).isPresent()) {
            systemFeedback.setId(id);
            return Optional.of(repository.save(systemFeedback));
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

}
