package cloudflight.integra.backend.hobbyGroup;


import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class HobbyGroupRepository {
    private final Map<UUID, HobbyGroup> groups = new HashMap<>();

    public List<HobbyGroup> findAll() {
        return new ArrayList<>(groups.values());
    }

    public Optional<HobbyGroup> findById(UUID id) {
        return Optional.ofNullable(groups.get(id));
    }

    public HobbyGroup save(HobbyGroup hobbyGroup) {
        if (hobbyGroup.getId() == null) {
            hobbyGroup.setId(UUID.randomUUID());
        }
        groups.put(hobbyGroup.getId(), hobbyGroup);
        return hobbyGroup;
    }

    public void deleteById(UUID id) {
        groups.remove(id);
    }


}
