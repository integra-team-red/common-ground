package cloudflight.integra.backend.systemFeedback;

import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class SystemFeedbackService {

    private final SystemFeedbackRepository repository;

    public SystemFeedbackService(SystemFeedbackRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<SystemFeedback> getAllPaginated(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public List<SystemFeedback> getAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public SystemFeedback getById(UUID id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public SystemFeedback create(SystemFeedback systemFeedback) {
        if (systemFeedback.getCreatedAt() == null) {
            systemFeedback.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(systemFeedback);
    }

    @Transactional
    public SystemFeedback update(UUID id, SystemFeedback newSystemFeedback) {
        SystemFeedback oldFeedback = repository.findById(id).orElseThrow();

        oldFeedback.setCreatedAt(newSystemFeedback.getCreatedAt());
        oldFeedback.setEmail(newSystemFeedback.getEmail());
        oldFeedback.setMessage(newSystemFeedback.getMessage());

        return oldFeedback;

    }

    @Transactional
    public boolean delete(UUID id) {
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        else throw new NoSuchElementException();
    }

}
