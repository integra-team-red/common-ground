package cloudflight.integra.backend.emailSystem;

import cloudflight.integra.backend.emailSystem.model.EmailAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmailAuditRepository extends JpaRepository<EmailAudit, UUID> {
}
