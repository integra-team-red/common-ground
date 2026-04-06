package cloudflight.integra.backend.emailSystem;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.email.enabled", havingValue = "true", matchIfMissing = false)
public class EmailNotificationListener {
    private final EmailService emailService;

    public EmailNotificationListener(EmailService emailService) {
        this.emailService = emailService;
    }

    @EventListener
    public void onUserJoinedGroup(UserJoinedGroupEvent event) {
        emailService.sendGroupJoinConfirmation(event.group(), event.user());
    }
}
