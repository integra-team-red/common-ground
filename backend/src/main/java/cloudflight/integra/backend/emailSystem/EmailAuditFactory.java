package cloudflight.integra.backend.emailSystem;

import cloudflight.integra.backend.emailSystem.model.EmailAudit;
import cloudflight.integra.backend.user.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class EmailAuditFactory {

    public EmailAudit buildSuccessfulEmailAudit(User user, String subject) {
        return new EmailAudit(
            null,
            user.getEmail(),
            subject,
            LocalDateTime.now(),
            "SUCCESS",
            null
        );
    }

    public EmailAudit buildFailedEmailAudit(User user, String subject, String errorMessage) {
        return new EmailAudit(
            null,
            user.getEmail(),
            subject,
            LocalDateTime.now(),
            "FAILURE",
            errorMessage
        );
    }
}
