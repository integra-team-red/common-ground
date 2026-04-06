package cloudflight.integra.backend.emailSystem;

import cloudflight.integra.backend.hobbyGroup.model.HobbyGroup;
import cloudflight.integra.backend.user.model.User;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;


@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailAuditRepository emailAuditRepository;
    private final EmailAuditFactory emailAuditFactory;

    public EmailService(
        JavaMailSender javaMailSender, EmailAuditRepository emailAuditRepository,
        EmailAuditFactory emailAuditFactory
    ) {
        this.javaMailSender = javaMailSender;
        this.emailAuditRepository = emailAuditRepository;
        this.emailAuditFactory = emailAuditFactory;
    }

    private SimpleMailMessage buildGroupJoinEmail(
        User currentUser,
        HobbyGroup hobbyGroupJoined,
        String subject
    ) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("commonground991@gmail.com");
        mailMessage.setTo(currentUser.getEmail());
        mailMessage.setText("You have successfully joined hobby group " + hobbyGroupJoined.getName() + "!" +
            " For more details, please contact the CommonGround support service!");
        mailMessage.setSubject(subject);
        return mailMessage;
    }

    public void sendGroupJoinConfirmation(HobbyGroup hobbyGroupJoined, User currentUser) {
        String subject = "Successfully joined hobby group " + hobbyGroupJoined.getName() + "!";
        try {
            SimpleMailMessage mailMessage = buildGroupJoinEmail(currentUser, hobbyGroupJoined, subject);
            javaMailSender.send(mailMessage);
            emailAuditRepository.save(emailAuditFactory.buildSuccessfulEmailAudit(currentUser, subject));

        } catch (MailException e) {
            emailAuditRepository.save(emailAuditFactory.buildFailedEmailAudit(currentUser, subject, e.getMessage()));
        }
    }
}
