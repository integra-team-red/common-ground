package cloudflight.integra.backend.systemFeedback;


import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class SystemFeedbackRepository {

    private final Map<UUID, SystemFeedback> systemFeedbacks = new HashMap<>();

    public List<SystemFeedback> findAll() {
        return new ArrayList<>(systemFeedbacks.values());
    }


    public Optional<SystemFeedback> findById(UUID id) {
        return Optional.ofNullable(systemFeedbacks.get(id));
    }


    public SystemFeedback save(SystemFeedback systemFeedback) {
        if (systemFeedback.getId() == null) {
            systemFeedback.setId(UUID.randomUUID());
        }
        systemFeedbacks.put(systemFeedback.getId(), systemFeedback);
        return systemFeedback;
    }


    public void deleteById(UUID id) {
        systemFeedbacks.remove(id);
    }
}
