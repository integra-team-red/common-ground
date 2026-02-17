package cloudflight.integra.backend.systemFeedback;


import cloudflight.integra.backend.systemFeedback.model.SystemFeedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface SystemFeedbackRepository extends JpaRepository<SystemFeedback, UUID> {
}
