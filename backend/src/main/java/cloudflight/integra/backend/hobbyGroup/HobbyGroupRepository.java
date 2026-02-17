package cloudflight.integra.backend.hobbyGroup;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface HobbyGroupRepository extends JpaRepository<HobbyGroup, UUID> {

}
